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

    private String relaStatus;

    private String relaFriendPhone;
    private String relaFriendRemark;
    private String relaFriendDesc;

    private String relaAuth;
    private String relaNotSeeMe;
    private String relaNotSeeHim;

    public UserRela() {

    }

    public UserRela(String relaUserId, String relaFriendId) {
        this.relaId = CommonUtil.generateId();
        this.relaUserId = relaUserId;
        this.relaFriendId = relaFriendId;
    }

    public UserRela(String relaUserId, String relaFriendId,
                    String relaFriendRemark, String relaFriendPhone, String relaFriendDesc) {
        this.relaId = CommonUtil.generateId();
        this.relaUserId = relaUserId;
        this.relaFriendId = relaFriendId;
        this.relaFriendRemark = relaFriendRemark;
        this.relaFriendPhone = relaFriendPhone;
        this.relaFriendDesc = relaFriendDesc;
    }

    public UserRela(String relaUserId, String relaFriendId,
                    String relaFriendRemark, String relaAuth, String relaNotSeeMe, String relaNotSeeHim) {
        this.relaId = CommonUtil.generateId();
        this.relaUserId = relaUserId;
        this.relaFriendId = relaFriendId;
        this.relaFriendRemark = relaFriendRemark;
        this.relaAuth = relaAuth;
        this.relaNotSeeMe = relaNotSeeMe;
        this.relaNotSeeHim = relaNotSeeHim;
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

    public String getRelaStatus() {
        return relaStatus;
    }

    public void setRelaStatus(String relaStatus) {
        this.relaStatus = relaStatus;
    }

    public String getRelaFriendPhone() {
        return relaFriendPhone;
    }

    public void setRelaFriendPhone(String relaFriendPhone) {
        this.relaFriendPhone = relaFriendPhone;
    }

    public String getRelaFriendRemark() {
        return relaFriendRemark;
    }

    public void setRelaFriendRemark(String relaFriendRemark) {
        this.relaFriendRemark = relaFriendRemark;
    }

    public String getRelaFriendDesc() {
        return relaFriendDesc;
    }

    public void setRelaFriendDesc(String relaFriendDesc) {
        this.relaFriendDesc = relaFriendDesc;
    }

    public String getRelaAuth() {
        return relaAuth;
    }

    public void setRelaAuth(String relaAuth) {
        this.relaAuth = relaAuth;
    }

    public String getRelaNotSeeMe() {
        return relaNotSeeMe;
    }

    public void setRelaNotSeeMe(String relaNotSeeMe) {
        this.relaNotSeeMe = relaNotSeeMe;
    }

    public String getRelaNotSeeHim() {
        return relaNotSeeHim;
    }

    public void setRelaNotSeeHim(String relaNotSeeHim) {
        this.relaNotSeeHim = relaNotSeeHim;
    }
}
