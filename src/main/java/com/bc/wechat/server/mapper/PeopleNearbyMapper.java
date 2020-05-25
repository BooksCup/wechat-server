package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.PeopleNearby;

import java.util.List;

/**
 * 附近的人
 *
 * @author zhou
 */
public interface PeopleNearbyMapper {
    /**
     * 保存位置信息
     *
     * @param peopleNearby 附近的人(位置信息)
     */
    void addPeopleNearby(PeopleNearby peopleNearby);

    /**
     * 修改位置信息
     *
     * @param peopleNearby 附近的人(位置信息)
     */
    void updatePeopleNearby(PeopleNearby peopleNearby);

    /**
     * 获取某个用户的位置信息列表
     *
     * @param userId 用户ID
     * @return 某个用户的位置信息列表
     */
    List<PeopleNearby> getPositionInfoListByUserId(String userId);
}
