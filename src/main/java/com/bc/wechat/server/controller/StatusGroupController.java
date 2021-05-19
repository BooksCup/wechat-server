package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.StatusGroup;
import com.bc.wechat.server.service.StatusGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 状态组
 *
 * @author zhou
 */
@RestController
@RequestMapping("statusGroup")
public class StatusGroupController {

    @Resource
    StatusGroupService statusGroupService;

    /**
     * 获取状态组列表
     *
     * @return 状态组列表
     */
    @ApiOperation(value = "获取状态组列表", notes = "获取状态组列表")
    @GetMapping(value = "")
    public ResponseEntity<List<StatusGroup>> getStatusGroupList() {

        ResponseEntity<List<StatusGroup>> responseEntity;
        try {
            List<StatusGroup> statusGroupList = statusGroupService.getStatusGroupList();
            responseEntity = new ResponseEntity<>(statusGroupList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}