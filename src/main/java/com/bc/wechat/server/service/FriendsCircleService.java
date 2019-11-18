package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.FriendsCircle;

import java.util.List;

/**
 * 朋友圈业务类接口
 *
 * @author zhou
 */
public interface FriendsCircleService {
    List<FriendsCircle> getFriendsCircleListByUserId(String userId);
}
