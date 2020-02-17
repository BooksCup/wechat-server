package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.Address;
import com.bc.wechat.server.mapper.AddressMapper;
import com.bc.wechat.server.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址
 *
 * @author zhou
 */
@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    /**
     * 根据用户ID获取用户地址列表
     *
     * @param userId 用户ID
     * @return 用户地址列表
     */
    @Override
    public List<Address> getAddressListByUserId(String userId) {
        return addressMapper.getAddressListByUserId(userId);
    }

    /**
     * 保存地址
     *
     * @param address 地址
     */
    @Override
    public void addAddress(Address address) {
        addressMapper.addAddress(address);
    }

    /**
     * 修改地址
     *
     * @param address 地址
     */
    @Override
    public void modifyAddress(Address address) {
        addressMapper.modifyAddress(address);
    }
}
