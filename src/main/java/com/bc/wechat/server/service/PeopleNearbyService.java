package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.PeopleNearby;

import java.util.List;
import java.util.Map;

/**
 * 附近的人
 *
 * @author zhou
 */
public interface PeopleNearbyService {

    /**
     * 上次位置信息
     *
     * @param peopleNearby 位置信息
     */
    void uploadPositionInfo(PeopleNearby peopleNearby);

    /**
     * 获取附近的人列表
     *
     * @param paramMap 参数map，包含用户ID(userId)和用户性别(userSex)
     * @return 附近的人列表
     */
    List<PeopleNearby> getPeopleNearbyList(Map<String, String> paramMap);

    /**
     * 清除某个用户的位置信息
     *
     * @param userId 用户ID
     */
    void deletePositionInfo(String userId);
}
