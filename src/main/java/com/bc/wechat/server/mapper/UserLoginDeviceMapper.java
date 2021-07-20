package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.UserLoginDevice;

import java.util.List;

/**
 * 用户登录设备信息
 *
 * @author zhou
 */
public interface UserLoginDeviceMapper {

    /**
     * 根据用户ID获取登录设备信息列表
     *
     * @param userId 用户ID
     * @return 登录设备信息列表
     */
    List<UserLoginDevice> getUserLoginDeviceListByUserId(String userId);

    /**
     * 新增用户登录设备信息
     *
     * @param userLoginDevice 用户登录设备信息
     */
    void addUserLoginDevice(UserLoginDevice userLoginDevice);

    /**
     * 修改用户登录设备信息
     *
     * @param userLoginDevice 用户登录设备信息
     */
    void updateUserLoginDevice(UserLoginDevice userLoginDevice);

    /**
     * 根据设备ID删除用户登录设备信息
     *
     * @param deviceId 设备ID
     */
    void deleteUserLoginDeviceByDeviceId(String deviceId);

}