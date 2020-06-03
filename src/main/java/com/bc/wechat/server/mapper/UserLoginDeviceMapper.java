package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.UserLoginDevice;

/**
 * 用户登录设备信息
 *
 * @author zhou
 */
public interface UserLoginDeviceMapper {

    /**
     * 新增用户登录设备信息
     *
     * @param userLoginDevice 用户登录设备信息
     */
    void addUserLoginDevice(UserLoginDevice userLoginDevice);
}
