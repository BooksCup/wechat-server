package com.bc.wechat.server.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CreateBucketRequest;
import com.bc.wechat.server.service.OssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // === bucket ===

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

    /**
     * 列举存储空间
     *
     * @return 存储空间列表
     */
    @Override
    public List<Bucket> listBuckets() {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 列举存储空间
        List<Bucket> buckets = ossClient.listBuckets();
        // 关闭OSSClient
        ossClient.shutdown();
        return buckets;
    }

    /**
     * 删除存储空间
     *
     * @param bucketName 存储空间的名称
     */
    @Override
    public void deleteBucket(String bucketName) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除存储空间
        ossClient.deleteBucket(bucketName);
        // 关闭OSSClient
        ossClient.shutdown();
    }
    // === bucket ===
}
