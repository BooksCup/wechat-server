package com.bc.wechat.server.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CreateBucketRequest;
import com.bc.wechat.server.service.OssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 对象存储oss
 *
 * @author zhou
 */
@Service("ossService")
public class OssServiceImpl implements OssService {
    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.key}")
    private String accessKeyId;

    @Value("${oss.secret}")
    private String accessKeySecret;

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间的名称
     */
    @Override
    public void createBucket(String bucketName) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 创建存储空间
        ossClient.createBucket(createBucketRequest);
        // 关闭OSSClient
        ossClient.shutdown();
    }
}
