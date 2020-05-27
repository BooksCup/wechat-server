package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.PeopleNearby;
import com.bc.wechat.server.mapper.PeopleNearbyMapper;
import com.bc.wechat.server.service.PeopleNearbyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
     * 上次位置信息
     *
     * @param peopleNearby 位置信息
     */
    @Override
    public void uploadPositionInfo(PeopleNearby peopleNearby) {
        List<PeopleNearby> positionInfoList = peopleNearbyMapper.getPositionInfoListByUserId(peopleNearby.getUserId());
        if (CollectionUtils.isEmpty(positionInfoList)) {
            peopleNearbyMapper.addPeopleNearby(peopleNearby);
        } else {
            peopleNearby.setId(positionInfoList.get(0).getId());
            peopleNearbyMapper.updatePeopleNearby(peopleNearby);
        }
    }

    /**
     * 获取附近的人列表
     *
     * @param paramMap 参数map，包含用户ID(userId)和用户性别(userSex)
     * @return 附近的人列表
     */
    @Override
    public List<PeopleNearby> getPeopleNearbyList(Map<String, String> paramMap) {
        List<PeopleNearby> peopleNearbyList = peopleNearbyMapper.getPeopleNearbyListByUserId(paramMap);
        return peopleNearbyList;
    }

    /**
     * 清除某个用户的位置信息
     *
     * @param userId 用户ID
     */
    @Override
    public void deletePositionInfo(String userId) {
        peopleNearbyMapper.deletePositionInfo(userId);
    }
}
