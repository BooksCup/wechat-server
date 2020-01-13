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
     * @param fromUserId    申请人用户ID
     * @param toUserId      接收人用户ID
     * @param relaRemark    好友备注
     * @param relaAuth      好友朋友权限 "0":聊天、朋友圈、微信运动  "1":仅聊天
     * @param relaNotSeeMe  朋友圈和视频动态 "0":可以看我 "1":不让他看我
     * @param relaNotSeeHim 朋友圈和视频动态 "0":可以看他 "1":不看他
     */
    void makeFriends(String fromUserId, String toUserId, String relaRemark,
                     String relaAuth, String relaNotSeeMe, String relaNotSeeHim);
}
