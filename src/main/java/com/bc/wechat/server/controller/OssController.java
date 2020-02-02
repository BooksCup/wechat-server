package com.bc.wechat.server.controller;

import com.aliyun.oss.model.Bucket;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 对象存储oss
 *
 * @author zhou
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    private static final Logger logger = LoggerFactory.getLogger(OssController.class);

    @Resource
    private OssService ossService;

    // === bucket ===

    /**
     * 创建存储空间
     * 存储空间（Bucket）是存储对象（Object）的容器。对象都隶属于存储空间。
     * 同一个存储空间的内部是扁平的，没有文件系统的目录等概念，所有的对象都直接隶属于其对应的存储空间。
     * 每个用户可以拥有多个存储空间。
     * 存储空间的名称在 OSS 范围内必须是全局唯一的，一旦创建之后无法修改名称。
     * 存储空间内部的对象数目没有限制。
     *
     * @param bucketName 存储空间的名称
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "创建存储空间", notes = "创建存储空间")
    @PostMapping(value = "/bucket")
    public ResponseEntity<String> createBucket(
            @RequestParam String bucketName) {
        ResponseEntity<String> responseEntity;
        try {
            ossService.createBucket(bucketName);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.OSS_CREATE_BUCKET_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[createBucket] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.OSS_CREATE_BUCKET_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 列举存储空间
     *
     * @return 存储空间列表
     */
    @ApiOperation(value = "列举存储空间", notes = "列举存储空间")
    @GetMapping(value = "/bucket")
    public ResponseEntity<List<Bucket>> listBuckets() {
        ResponseEntity<List<Bucket>> responseEntity;
        try {
            List<Bucket> bucketList = ossService.listBuckets();
            responseEntity = new ResponseEntity<>(bucketList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[listBuckets] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除存储空间
     *
     * @param bucketName 存储空间的名称
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "删除存储空间", notes = "删除存储空间")
    @DeleteMapping(value = "/bucket")
    public ResponseEntity<String> deleteBucket(
            @RequestParam String bucketName) {
        ResponseEntity<String> responseEntity;
        try {
            ossService.deleteBucket(bucketName);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.OSS_DELETE_BUCKET_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[deleteBucket] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.OSS_DELETE_BUCKET_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // === bucket ===

    /**
     * 上传文件至oss
     *
     * @param request 请求
     * @return 上传成功的文件URL列表
     */
    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping(value = "/file")
    public ResponseEntity<List<String>> uploadFile(HttpServletRequest request) {
        try {
            List<String> imgUrlList = new ArrayList<>();

            // 检测是不是存在上传文件
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            // 判断 request 是否有文件上传,即多部分请求
            if (isMultipart) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    String originFileName = file.getOriginalFilename();
                    String prefix = originFileName.substring(originFileName.lastIndexOf(".") + 1);
                    if (file != null) {
                        try {
                            // //取得当前上传文件的文件名称
                            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + '.' + prefix;
                            ossService.putObject("erp-wd-com", fileName, file.getBytes());
                            imgUrlList.add(fileName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return new ResponseEntity<>(imgUrlList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
