package com.bc.wechat.server.controller;

import com.aliyun.oss.model.Bucket;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
}
