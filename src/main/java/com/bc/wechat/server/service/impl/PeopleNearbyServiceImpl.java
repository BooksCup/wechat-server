package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.PeopleNearby;
import com.bc.wechat.server.mapper.PeopleNearbyMapper;
import com.bc.wechat.server.service.PeopleNearbyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 附近的人
 *
 * @author zhou
 */
@Service("peopleNearbyService")
public class PeopleNearbyServiceImpl implements PeopleNearbyService {
    @Resource
    private PeopleNearbyMapper peopleNearbyMapper;

    /**
     * 上传位置信息并获取附近的人列表
     *
     * @param peopleNearby 附近的人(位置信息)
     */
    @Override
    public void getPeopleNearbyList(PeopleNearby peopleNearby) {
        peopleNearbyMapper.addPeopleNearby(peopleNearby);
    }
}
