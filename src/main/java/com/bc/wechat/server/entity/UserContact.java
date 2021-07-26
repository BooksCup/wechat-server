package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 用户联系人(通讯录)
 *
 * @author zhou
 */
public class UserContact {

    private String contactId;
    private String userId;
    private String contactUserId;
    private String status;

    private String contactAlias;
    private String contactTags;
    private String contactMobiles;
    private String contactDesc;

    /**
     * 联系人来源
     */
    private String contactFrom;

    private String privacy;
    private String hideMyPosts;
    private String hideHisPosts;

    private String isStarred;
    private String isBlocked;
    private String createTime;

    public UserContact() {

    }

    public UserContact(String userId, String contactUserId) {
        this.contactId = CommonUtil.generateId();
        this.userId = userId;
        this.contactUserId = contactUserId;
    }

    public UserContact(String userId, String contactUserId,
                       String contactAlias, String contactMobiles, String contactDesc) {
        this.contactId = CommonUtil.generateId();
        this.userId = userId;
        this.contactUserId = contactUserId;
        this.contactAlias = contactAlias;
        this.contactMobiles = contactMobiles;
        this.contactDesc = contactDesc;
    }

    public UserContact(String userId, String contactUserId,
                       String contactAlias, String privacy, String hideMyPosts, String hideHisPosts) {
        this.contactId = CommonUtil.generateId();
        this.userId = userId;
        this.contactUserId = contactUserId;
        this.contactAlias = contactAlias;
        this.privacy = privacy;
        this.hideMyPosts = hideMyPosts;
        this.hideHisPosts = hideHisPosts;
    }

    public UserContact(String contactId, String privacy, String hideMyPosts, String hideHisPosts) {
        this.contactId = contactId;
        this.privacy = privacy;
        this.hideMyPosts = hideMyPosts;
        this.hideHisPosts = hideHisPosts;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(String contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactAlias() {
        return contactAlias;
    }

    public void setContactAlias(String contactAlias) {
        this.contactAlias = contactAlias;
    }

    public String getContactTags() {
        return contactTags;
    }

    public void setContactTags(String contactTags) {
        this.contactTags = contactTags;
    }

    public String getContactMobiles() {
        return contactMobiles;
    }

    public void setContactMobiles(String contactMobiles) {
        this.contactMobiles = contactMobiles;
    }

    public String getContactDesc() {
        return contactDesc;
    }

    public void setContactDesc(String contactDesc) {
        this.contactDesc = contactDesc;
    }

    public String getContactFrom() {
        return contactFrom;
    }

    public void setContactFrom(String contactFrom) {
        this.contactFrom = contactFrom;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getHideMyPosts() {
        return hideMyPosts;
    }

    public void setHideMyPosts(String hideMyPosts) {
        this.hideMyPosts = hideMyPosts;
    }

    public String getHideHisPosts() {
        return hideHisPosts;
    }

    public void setHideHisPosts(String hideHisPosts) {
        this.hideHisPosts = hideHisPosts;
    }

    public String getIsStarred() {
        return isStarred;
    }

    public void setIsStarred(String isStarred) {
        this.isStarred = isStarred;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}