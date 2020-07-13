package com.bc.wechat.server.entity.search;

import com.bc.wechat.server.utils.CommonUtil;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 文件实体
 * 对应es中的文件文档
 * index: file_item
 * type: file_item
 *
 * @author zhou
 */
@Document(indexName = "file_item", type = "file_item", shards = 1, replicas = 0, refreshInterval = "-1")
public class FileItem {
    private String id;

    private String fileName;
    private String filePath;
    private String diskName;
    private String fileSize;

    public FileItem() {

    }

    public FileItem(String fileName, String filePath, String diskName, String fileSize) {
        this.id = CommonUtil.generateId();
        this.fileName = fileName;
        this.filePath = filePath;
        this.diskName = diskName;
        this.fileSize = fileSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
