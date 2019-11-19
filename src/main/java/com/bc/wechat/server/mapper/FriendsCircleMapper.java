package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.FriendsCircle;

import java.util.List;

/**
 * 朋友圈dao
 *
 * @author zhou
 */
public interface FriendsCircleMapper {

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param userId 用户ID
     * @return 朋友圈列表
     */
    List<FriendsCircle> getFriendsCircleListByUserId(String userId);
}
