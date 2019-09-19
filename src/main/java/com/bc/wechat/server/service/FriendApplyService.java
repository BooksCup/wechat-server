package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.FriendApply;

/**
 * 好友申请业务类接口
 * @author zhou
 */
public interface FriendApplyService {
    /**
     * 新增好友申请
     *
     * @param friendApply 好友申请
     */
    void addFriendApply(FriendApply friendApply);
}
