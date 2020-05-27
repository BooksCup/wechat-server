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

    /**
     * 获取某个用户的附近的人列表
     *
     * @param peopleNearby 某个用户的个人信息及位置信息
     * @return 某个用户的附近的人列表
     */
    List<PeopleNearby> getPeopleNearbyListByUserId(PeopleNearby peopleNearby);

    /**
     * 清除某个用户的位置信息
     *
     * @param userId 用户ID
     */
    void deletePositionInfo(String userId);
}
