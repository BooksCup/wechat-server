package com.bc.wechat.server.service;

import com.aliyun.oss.model.Bucket;

import java.util.List;

/**
 * 对象存储oss
 *
 * @author zhou
 */
public interface OssService {

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间的名称
     */
    void createBucket(String bucketName);

    /**
     * 列举存储空间
     *
     * @return 存储空间列表
     */
    List<Bucket> listBuckets();

    /**
     * 删除存储空间
     *
     * @param bucketName 存储空间的名称
     */
    void deleteBucket(String bucketName);
}
