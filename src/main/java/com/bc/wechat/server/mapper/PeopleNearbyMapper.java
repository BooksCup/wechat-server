package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.PeopleNearby;

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
}
