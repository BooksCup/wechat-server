package com.bc.wechat.server.controller;

import com.bc.wechat.server.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 资源上传及下载
 *
 * @author zhou
 */
@Api(value = "resources", tags = {"resources"})
@RestController
@RequestMapping("resources")
public class ResourceProvider {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "下载文件", notes = "下载文件")
    @GetMapping(value = "")
    public ResponseEntity<String> downloadFile(@RequestParam String mediaId) {
        return resourceService.downloadFile(mediaId);
    }

}
