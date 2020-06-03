package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.UserLoginDevice;
import com.bc.wechat.server.mapper.UserLoginDeviceMapper;
import com.bc.wechat.server.service.UserLoginDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户登录设备信息
 *
 * @author zhou
 */
@Service("userLoginDeviceService")
public class UserLoginDeviceServiceImpl implements UserLoginDeviceService {

    @Resource
    private UserLoginDeviceMapper userLoginDeviceMapper;

    /**
     * 新增用户登录设备信息
     *
     * @param userLoginDevice 用户登录设备信息
     */
    @Override
    public void addUserLoginDevice(UserLoginDevice userLoginDevice) {
        userLoginDeviceMapper.addUserLoginDevice(userLoginDevice);
    }
}
