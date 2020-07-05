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
    private String relaContactId;
    private String relaCreateTime;

    private String relaStatus;

    private String relaContactAlias;
    private String relaContactTags;
    private String relaContactMobiles;
    private String relaContactDesc;

    private String relaPrivacy;
    private String relaHideMyPosts;
    private String relaHideHisPosts;

    private String relaIsStarFriend;

    public UserRela() {

    }

    public UserRela(String relaUserId, String relaContactId) {
        this.relaId = CommonUtil.generateId();
        this.relaUserId = relaUserId;
        this.relaContactId = relaContactId;
    }

    public UserRela(String relaUserId, String relaContactId,
                    String relaContactAlias, String relaContactMobiles, String relaContactDesc) {
        this.relaId = CommonUtil.generateId();
        this.relaUserId = relaUserId;
        this.relaContactId = relaContactId;
        this.relaContactAlias = relaContactAlias;
        this.relaContactMobiles = relaContactMobiles;
        this.relaContactDesc = relaContactDesc;
    }

    public UserRela(String relaUserId, String relaContactId,
                    String relaContactAlias, String relaPrivacy, String relaHideMyPosts, String relaHideHisPosts) {
        this.relaId = CommonUtil.generateId();
        this.relaUserId = relaUserId;
        this.relaContactId = relaContactId;
        this.relaContactAlias = relaContactAlias;
        this.relaPrivacy = relaPrivacy;
        this.relaHideMyPosts = relaHideMyPosts;
        this.relaHideHisPosts = relaHideHisPosts;
    }

    public UserRela(String relaId, String relaPrivacy, String relaHideMyPosts, String relaHideHisPosts) {
        this.relaId = relaId;
        this.relaPrivacy = relaPrivacy;
        this.relaHideMyPosts = relaHideMyPosts;
        this.relaHideHisPosts = relaHideHisPosts;
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

    public String getRelaContactId() {
        return relaContactId;
    }

    public void setRelaContactId(String relaContactId) {
        this.relaContactId = relaContactId;
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

    public String getRelaContactAlias() {
        return relaContactAlias;
    }

    public void setRelaContactAlias(String relaContactAlias) {
        this.relaContactAlias = relaContactAlias;
    }

    public String getRelaContactMobiles() {
        return relaContactMobiles;
    }

    public void setRelaContactMobiles(String relaContactMobiles) {
        this.relaContactMobiles = relaContactMobiles;
    }

    public String getRelaContactTags() {
        return relaContactTags;
    }

    public void setRelaContactTags(String relaContactTags) {
        this.relaContactTags = relaContactTags;
    }

    public String getRelaContactDesc() {
        return relaContactDesc;
    }

    public void setRelaContactDesc(String relaContactDesc) {
        this.relaContactDesc = relaContactDesc;
    }

    public String getRelaPrivacy() {
        return relaPrivacy;
    }

    public void setRelaPrivacy(String relaPrivacy) {
        this.relaPrivacy = relaPrivacy;
    }

    public String getRelaHideMyPosts() {
        return relaHideMyPosts;
    }

    public void setRelaHideMyPosts(String relaHideMyPosts) {
        this.relaHideMyPosts = relaHideMyPosts;
    }

    public String getRelaHideHisPosts() {
        return relaHideHisPosts;
    }

    public void setRelaHideHisPosts(String relaHideHisPosts) {
        this.relaHideHisPosts = relaHideHisPosts;
    }

    public String getRelaIsStarFriend() {
        return relaIsStarFriend;
    }

    public void setRelaIsStarFriend(String relaIsStarFriend) {
        this.relaIsStarFriend = relaIsStarFriend;
    }
}
