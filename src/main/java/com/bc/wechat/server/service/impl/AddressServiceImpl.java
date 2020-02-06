package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.Address;
import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.mapper.AddressMapper;
import com.bc.wechat.server.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
}
