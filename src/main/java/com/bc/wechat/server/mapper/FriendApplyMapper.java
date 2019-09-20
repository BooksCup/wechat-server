package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.FriendApply;
import com.bc.wechat.server.entity.UserRela;

import java.util.List;
import java.util.Map;

/**
 * 好友申请dao
 *
 * @author zhou
 */
public interface FriendApplyMapper {
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
}
