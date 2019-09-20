package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 用户关系
 *
 * @author zhou
 */
public class UserRela {
    private String relaId;
    private String relaUserId;
    private String relaFriendId;
    private String relaCreateTime;

    public UserRela() {

    }

    public UserRela(String relaUserId, String relaFriendId) {
        this.relaId = CommonUtil.generateId();
        this.relaUserId = relaUserId;
        this.relaFriendId = relaFriendId;
    }

    public String getRelaId() {
        return relaId;
    }

    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }

    public String getRelaUserId() {
        return relaUserId;
    }

    public void setRelaUserId(String relaUserId) {
        this.relaUserId = relaUserId;
    }

    public String getRelaFriendId() {
        return relaFriendId;
    }

    public void setRelaFriendId(String relaFriendId) {
        this.relaFriendId = relaFriendId;
    }

    public String getRelaCreateTime() {
        return relaCreateTime;
    }

    public void setRelaCreateTime(String relaCreateTime) {
        this.relaCreateTime = relaCreateTime;
    }
}
