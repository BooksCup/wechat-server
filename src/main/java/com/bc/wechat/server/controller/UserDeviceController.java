package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.UserLoginDevice;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.UserLoginDeviceService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录设备信息
 *
 * @author zhou
 */
@RestController
@RequestMapping("/users")
public class UserDeviceController {

    private static final Logger logger = LoggerFactory.getLogger(UserDeviceController.class);

    @Resource
    UserLoginDeviceService userLoginDeviceService;

    /**
     * 获取用户的登录设备信息列表
     *
     * @param userId 用户ID
     * @return 登录设备信息列表
     */
    @ApiOperation(value = "获取用户的登录设备信息列表", notes = "获取用户的登录设备信息列表")
    @GetMapping(value = "/{userId}/devices")
    public ResponseEntity<List<UserLoginDevice>> getDeviceInfoListByUserId(
            @PathVariable String userId) {
        ResponseEntity<List<UserLoginDevice>> responseEntity;
        try {
            List<UserLoginDevice> userLoginDeviceList = userLoginDeviceService.getUserLoginDeviceListByUserId(userId);
            responseEntity = new ResponseEntity<>(userLoginDeviceList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getDeviceInfoListByUserId] error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改登录设备信息
     *
     * @param deviceId        登录设备ID
     * @param phoneModelAlias 手机型号别名(用户备注)
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改登录设备信息", notes = "修改登录设备信息")
    @PutMapping(value = "/{userId}/devices/{deviceId}")
    public ResponseEntity<String> modifyDevice(
            @PathVariable String deviceId,
            @RequestParam String phoneModelAlias) {
        ResponseEntity<String> responseEntity;
        try {
            UserLoginDevice userLoginDevice = new UserLoginDevice();
            userLoginDevice.setDeviceId(deviceId);
            userLoginDevice.setPhoneModelAlias(phoneModelAlias);
            userLoginDeviceService.updateUserLoginDevice(userLoginDevice);

            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[modifyDevice] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除登录设备信息
     *
     * @param deviceId 登录设备ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "删除登录设备信息", notes = "删除登录设备信息")
    @DeleteMapping(value = "/{userId}/devices/{deviceId}")
    public ResponseEntity<String> deleteDevice(
            @PathVariable String deviceId) {
        ResponseEntity<String> responseEntity;
        try {
            userLoginDeviceService.deleteUserLoginDeviceByDeviceId(deviceId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[deleteDevice] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SUCCESS.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}