package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.PeopleNearby;
import com.bc.wechat.server.mapper.PeopleNearbyMapper;
import com.bc.wechat.server.service.PeopleNearbyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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
    public List<PeopleNearby> getPeopleNearbyList(PeopleNearby peopleNearby) {
        List<PeopleNearby> positionInfoList = peopleNearbyMapper.getPositionInfoListByUserId(peopleNearby.getUserId());
        if (CollectionUtils.isEmpty(positionInfoList)) {
            peopleNearbyMapper.addPeopleNearby(peopleNearby);
        } else {
            peopleNearby.setId(positionInfoList.get(0).getId());
            peopleNearbyMapper.updatePeopleNearby(peopleNearby);
        }

        List<PeopleNearby> peopleNearbyList = peopleNearbyMapper.getPeopleNearbyListByUserId(peopleNearby);
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
