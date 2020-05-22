package com.bc.wechat.server.service;

import com.aliyun.oss.model.Bucket;

import java.io.File;
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

    /**
     * 上传文件(简单上传)
     *
     * @param bucketName 存储空间的名称
     * @param fileName   文件名
     * @param content    上传内容(Byte数组)
     * @return 存储在oss的文件的绝对路径
     */
    String putObject(String bucketName, String fileName, byte[] content);

    /**
     * 上传文件(简单上传)
     *
     * @param bucketName 存储空间的名称
     * @param fileName   文件名
     * @param file       文件
     * @return 存储在oss的文件的绝对路径
     */
    String putObject(String bucketName, String fileName, File file);
}
