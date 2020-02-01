package com.bc.wechat.server.service;

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
}
