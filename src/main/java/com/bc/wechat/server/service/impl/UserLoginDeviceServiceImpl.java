package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.Address;
import com.bc.wechat.server.entity.UserLoginDevice;
import com.bc.wechat.server.mapper.UserLoginDeviceMapper;
import com.bc.wechat.server.service.UserLoginDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 根据用户ID获取登录设备信息列表
     *
     * @param userId 用户ID
     * @return 登录设备信息列表
     */
    @Override
    public List<UserLoginDevice> getUserLoginDeviceListByUserId(String userId) {
        return userLoginDeviceMapper.getUserLoginDeviceListByUserId(userId);
    }


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
