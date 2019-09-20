package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.FriendApply;

/**
 * 好友申请业务类接口
 *
 * @author zhou
 */
public interface FriendApplyService {

    /**
     * 新增好友申请
     *
     * @param friendApply 好友申请
     */
    void addFriendApply(FriendApply friendApply);

    /**
     * 接受好友申请
     *
     * @param applyId 申请ID
     */
    void acceptFriendApply(String applyId);

    /**
     * 根据申请ID获取好友申请
     *
     * @param applyId 申请ID
     * @return 好友申请
     */
    FriendApply getFriendApplyById(String applyId);

    /**
     * 交朋友
     *
     * @param fromUserId 第一个用户ID
     * @param toUserId   第二个用户ID
     */
    void makeFriends(String fromUserId, String toUserId);
}
