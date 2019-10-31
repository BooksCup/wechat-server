package com.bc.wechat.server.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.resource.DownloadResult;
import com.bc.wechat.server.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 资源业务类实现类
 *
 * @author zhou
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Resource
    private JMessageClient jMessageClient;

    /**
     * 下载文件
     *
     * @param mediaId mediaId
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<String> downloadFile(String mediaId) {
        ResponseEntity<String> responseEntity;
        try {
            DownloadResult downloadResult = jMessageClient.downloadFile(mediaId);
            responseEntity = new ResponseEntity<>(downloadResult.getUrl(), HttpStatus.OK);
        } catch (APIConnectionException e) {
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (APIRequestException e) {
            logger.error("status: " + e.getStatus() + ",errorCode: "
                    + e.getErrorCode() + ",errorMessage: "
                    + e.getErrorMessage());
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
