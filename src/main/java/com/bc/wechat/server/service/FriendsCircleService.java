package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.FriendsCircle;

import java.util.List;

/**
 * 朋友圈业务类接口
 *
 * @author zhou
 */
public interface FriendsCircleService {
    /**
     * 查找某个用户的朋友圈列表
     *
     * @param userId 用户ID
     * @return 朋友圈列表
     */
    List<FriendsCircle> getFriendsCircleListByUserId(String userId);
}
