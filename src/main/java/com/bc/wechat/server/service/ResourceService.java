package com.bc.wechat.server.service;

import org.springframework.http.ResponseEntity;

/**
 * 资源业务类接口
 *
 * @author zhou
 */
public interface ResourceService {
    /**
     * 下载文件
     *
     * @param mediaId mediaId
     * @return ResponseEntity
     */
    ResponseEntity<String> downloadFile(String mediaId);
}
