package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.Address;

import java.util.List;

/**
 * 地址
 *
 * @author zhou
 */
public interface AddressService {
    /**
     * 根据用户ID获取用户地址列表
     *
     * @param userId 用户ID
     * @return 用户地址列表
     */
    List<Address> getAddressListByUserId(String userId);

    /**
     * 保存地址
     *
     * @param address 地址
     */
    void addAddress(Address address);
}
