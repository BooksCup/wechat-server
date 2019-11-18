package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.FriendsCircle;

import java.util.List;

/**
 * 朋友圈dao
 *
 * @author zhou
 */
public interface FriendsCircleMapper {
    List<FriendsCircle> getFriendsCircleListByUserId(String userId);
}
