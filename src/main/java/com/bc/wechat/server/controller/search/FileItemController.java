package com.bc.wechat.server.controller.search;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.search.FileItem;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.FileItemService;
import com.bc.wechat.server.utils.FileUtil;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 文件
 *
 * @author zhou
 */
@RestController
@RequestMapping("/fileItems")
public class FileItemController {

    private static final Logger logger = LoggerFactory.getLogger(FileItemController.class);

    private static final int THREAD_NUM = 10;
    @Resource
    FileItemService fileItemService;

    /**
     * 创建文件文档
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param diskName 磁盘名
     * @param fileSize 文件大小
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "创建文件文档", notes = "创建文件文档")
    @PostMapping(value = "")
    public ResponseEntity<String> createFileItemDocument(
            @RequestParam String fileName,
            @RequestParam String filePath,
            @RequestParam String diskName,
            @RequestParam String fileSize) {
        ResponseEntity<String> responseEntity;
        FileItem fileItem = new FileItem(fileName, filePath, diskName, fileSize);
        try {
            fileItemService.save(fileItem);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_CREATE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_CREATE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 批量创建文件文档
     *
     * @param path      文件路径
     * @param diskName  磁盘名
     * @param threadNum 处理线程数
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "批量创建文件文档", notes = "批量创建文件文档")
    @PostMapping(value = "/batch")
    public ResponseEntity<String> batchCreateFileItemDocument(
            @RequestParam String path,
            @RequestParam String diskName,
            @RequestParam(value = "threadNum", required = false) Integer threadNum) {
        ResponseEntity<String> responseEntity;
        long startTimeStamp = System.currentTimeMillis();
        try {
            threadNum = (null == threadNum || threadNum <= 0) ? THREAD_NUM : threadNum;

            File pathfile = new File(path);
            if (!pathfile.isDirectory()) {
                return new ResponseEntity<>(ResponseMsg.PATH_IS_ILLEGAL.getResponseCode(), HttpStatus.BAD_REQUEST);
            }

            List<File> fileList = new ArrayList<>();
            try {
                FileUtil.resetFileList();
                fileList = FileUtil.getFileList(path);
                logger.info("[batchCreateFileItemDocument] fileList's size: " + fileList.size());

            } catch (Exception e) {
                e.printStackTrace();
            }

            // 开启10个线程处理
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);

            List<List<File>> littleFileList = FileUtil.splitFileList(fileList, threadNum);
            CountDownLatch countDownLatch = new CountDownLatch(littleFileList.size());
            for (List<File> littleUnit : littleFileList) {
                fixedThreadPool.execute(() -> {
                    for (File file : littleUnit) {
                        FileItem fileItem = new FileItem(file.getName(), file.getPath(), diskName, FileUtil.getShowSize(file.length()));
                        fileItemService.save(fileItem);
                    }
                    countDownLatch.countDown();
                });
            }
            try {
                countDownLatch.await();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_BATCH_CREATE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_BATCH_CREATE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("[batchCreateFileItemDocument] all finished.");
        long endTimeStamp = System.currentTimeMillis();
        logger.info("[batchCreateFileItemDocument] cost:" + (endTimeStamp - startTimeStamp) + "ms.");
        return responseEntity;
    }

    /**
     * 根据磁盘名删除文件文档
     *
     * @param diskName 磁盘名
     */
    @ApiOperation(value = "根据磁盘名删除文件文档", notes = "根据磁盘名删除文件文档")
    @DeleteMapping(value = "")
    public ResponseEntity<String> deleteFileItemByDiskName(
            @RequestParam String diskName) {
        ResponseEntity<String> responseEntity;
        try {
            fileItemService.deleteFileItemByDiskName(diskName);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除文件索引
     */
    @ApiOperation(value = "删除文件索引", notes = "删除文件索引")
    @DeleteMapping(value = "/index")
    public ResponseEntity<String> deleteFileItemIndex() {
        ResponseEntity<String> responseEntity;
        try {
            fileItemService.deleteFileItemIndex();
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_INDEX_DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_INDEX_DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 搜索文件(版本号v1: 简单查询)
     *
     * @param searchKey     关键字
     * @param page          页数(默认第1页)
     * @param pageSize      分页大小(默认单页10条)
     * @param sortField     排序字段
     * @param sortDirection ASC: "升序"  DESC:"降序"
     * @return Page<FileItem>
     */
    @ApiOperation(value = "搜索文件(版本号v1: 简单查询)", notes = "搜索文件(版本号v1: 简单查询)")
    @GetMapping(value = "/v1")
    public Page<FileItem> searchV1(@RequestParam String searchKey,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "sortField", required = false) String sortField,
                                   @RequestParam(value = "sortDirection", required = false) String sortDirection) {
        logger.info("[searchV1], searchKey: " + searchKey);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(searchKey)) {
            // must
            // 根据关键字匹配若干字段,文件名、文件路径
            MultiMatchQueryBuilder keywordMmqb = QueryBuilders.multiMatchQuery(searchKey,
                    "fileName", "filePath", "fileName.pinyin");
            boolQuery = boolQuery.must(keywordMmqb);
        }
        Pageable pageable = generatePageable(page, pageSize, sortField, sortDirection);
        return fileItemService.search(boolQuery, pageable);
    }

    /**
     * 搜索文件(版本号v3: 高亮+拼音 查询)
     * 返回pageInfo
     *
     * @param searchKey     搜索关键字
     * @param page          页数(默认第1页)
     * @param limit         分页大小(默认单页10条)
     * @param sortField     排序字段
     * @param sortDirection ASC: "升序"  DESC:"降序"
     * @param highLightFlag 高亮FLAG开关 0:"关闭"  1:"开启" 默认开启
     * @return 搜索结果
     */
    @ApiOperation(value = "搜索文件(版本号v3: 复杂查询(高亮+拼音), 返回pageInfo)", notes = "搜索文件(版本号v3: 复杂查询(高亮+拼音), 返回pageInfo)")
    @GetMapping(value = "/v3")
    public ResponseEntity<Page<FileItem>> searchV3(@RequestParam(required = false) String searchKey,
                                                   @RequestParam(required = false) String diskName,
                                                   @RequestParam(value = "color", required = false, defaultValue = "red") String color,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                   @RequestParam(value = "sortField", required = false) String sortField,
                                                   @RequestParam(value = "sortDirection", required = false) String sortDirection,
                                                   @RequestParam(value = "highLightFlag", required = false, defaultValue = "1") String highLightFlag) {
        logger.info("[searchV3], searchKey: " + searchKey);
        ResponseEntity<Page<FileItem>> responseEntity;
        try {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

            List<String> highLightFieldList = new ArrayList<>();
            highLightFieldList.add("fileName");
            highLightFieldList.add("filePath");
            highLightFieldList.add("fileName" + Constant.FIELD_TYPE_PINYIN);
            highLightFieldList.add("filePath" + Constant.FIELD_TYPE_PINYIN);

            // 关闭高亮可以直接清除所有高亮域
            if (Constant.HIGHLIGHT_FLAG_CLOSE.equals(highLightFlag)) {
                highLightFieldList.clear();
            }

            if (!StringUtils.isEmpty(searchKey)) {
                // must
                // 根据关键字匹配若干字段,商品名称、SEO关键字及SEO描述
                MultiMatchQueryBuilder keywordMmqb = QueryBuilders.multiMatchQuery(searchKey,
                        "fileName", "filePath",
                        "fileName" + Constant.FIELD_TYPE_PINYIN, "filePath" + Constant.FIELD_TYPE_PINYIN);
                boolQuery = boolQuery.must(keywordMmqb);
            }

            QueryBuilder postFilter = null;
            if (!StringUtils.isEmpty(diskName)) {
                // 根据diskName过滤
                postFilter = QueryBuilders.termQuery("diskName", diskName);
            }

            HighlightBuilder.Field[] highLightFields = new HighlightBuilder.Field[highLightFieldList.size()];

            for (int i = 0; i < highLightFieldList.size(); i++) {
                highLightFields[i] = new HighlightBuilder.Field(highLightFieldList.get(i))
                        .preTags("<font color='" + color + "'>")
                        .postTags("</font>")
                        .fragmentSize(250);
            }

            Pageable pageable = generatePageable(page, limit, sortField, sortDirection);
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQuery).withPageable(pageable);
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withHighlightFields(highLightFields);
            if (null != postFilter) {
                nativeSearchQueryBuilder = nativeSearchQueryBuilder.withFilter(postFilter);
            }
            SearchQuery searchQuery = nativeSearchQueryBuilder.build();
            Page<FileItem> fileItemPage = fileItemService.complexSearch(searchQuery, highLightFieldList);

            responseEntity = new ResponseEntity<>(fileItemPage, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[searchV3] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new AggregatedPageImpl<>(new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 搜索文件(版本号v4: 高亮+拼音 查询)
     * 返回list
     *
     * @param searchKey     搜索关键字
     * @param page          页数(默认第1页)
     * @param limit         分页大小(默认单页10条)
     * @param sortField     排序字段
     * @param sortDirection ASC: "升序"  DESC:"降序"
     * @param highLightFlag 高亮FLAG开关 0:"关闭"  1:"开启" 默认开启
     * @return 搜索结果
     */
    @ApiOperation(value = "搜索文件(版本号v4: 复杂查询(高亮+拼音), 返回list)", notes = "搜索文件(版本号v4: 复杂查询(高亮+拼音), 返回list)")
    @GetMapping(value = "/v4")
    public ResponseEntity<List<FileItem>> searchV4(@RequestParam(required = false) String searchKey,
                                                   @RequestParam(required = false) String diskName,
                                                   @RequestParam(value = "color", required = false, defaultValue = "red") String color,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                                   @RequestParam(value = "sortField", required = false) String sortField,
                                                   @RequestParam(value = "sortDirection", required = false) String sortDirection,
                                                   @RequestParam(value = "highLightFlag", required = false, defaultValue = "1") String highLightFlag) {
        logger.info("[searchV4], searchKey: " + searchKey + ", color: " + color + ", page: " + page);
        ResponseEntity<List<FileItem>> responseEntity;
        try {
            ResponseEntity<Page<FileItem>> response = searchV3(searchKey, diskName, color, page,
                    limit, sortField, sortDirection, highLightFlag);
            if (response.getBody().getTotalPages() < page) {
                logger.info("[searchV4], empty list");
                responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            } else {
                logger.info("[searchV4], list.size: " + response.getBody().getContent().size());
                responseEntity = new ResponseEntity<>(response.getBody().getContent(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[searchV4] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 文件搜索词补齐
     *
     * @param prefix  搜索前缀
     * @param topSize 搜索结果数量
     * @return 搜索结果列表
     */
    @ApiOperation(value = "文件搜索词补齐", notes = "文件搜索词补齐")
    @GetMapping(value = "/suggest")
    public ResponseEntity<List<String>> suggest(@RequestParam(required = false) String prefix,
                                                @RequestParam(value = "topSize", required = false, defaultValue = "5") Integer topSize) {
        logger.info("[suggest] prefix: " + prefix);
        ResponseEntity<List<String>> responseEntity;
        try {
            if (StringUtils.isEmpty(prefix)) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            List<String> suggestList = fileItemService.suggestSearch("suggest", prefix, topSize);
            responseEntity = new ResponseEntity<>(suggestList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("[suggest] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 获取磁盘名列表
     *
     * @return 磁盘名列表
     */
    @ApiOperation(value = "获取磁盘名列表", notes = "获取磁盘名列表")
    @GetMapping(value = "/diskName")
    public ResponseEntity<List<String>> getDiskNameList() {
        ResponseEntity<List<String>> responseEntity;
        try {
            List<String> diskNameList = fileItemService.getDiskNameList();
            responseEntity = new ResponseEntity<>(diskNameList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("[getDiskNameList] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 生成分页参数
     *
     * @param page          页数(默认第1页)
     * @param pageSize      分页大小(默认单页10条)
     * @param sortField     排序字段
     * @param sortDirection ASC: "升序"  DESC:"降序"
     * @return 分页参数
     */
    private Pageable generatePageable(Integer page, Integer pageSize, String sortField, String sortDirection) {
        page = page < 1 ? 0 : page - 1;
        Pageable pageable;
        if (!StringUtils.isEmpty(sortField)) {
            // 需要排序
            Sort.Direction direction;
            if (Constant.SORT_DIRECTION_ASC.equalsIgnoreCase(sortDirection)) {
                direction = Sort.Direction.ASC;
            } else {
                direction = Sort.Direction.DESC;
            }
            Sort sort = new Sort(direction, sortField);
            pageable = PageRequest.of(page, pageSize, sort);
        } else {
            pageable = PageRequest.of(page, pageSize);
        }
        return pageable;
    }
}
