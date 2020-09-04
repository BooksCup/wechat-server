package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 用户联系人标签
 *
 * @author zhou
 */
public class UserContactTag {
    private String tagId;
    private String userId;
    private String contactId;
    private String tagName;
    private String createTime;

    public UserContactTag() {

    }

    public UserContactTag(String userId, String contactId, String tagName) {
        this.tagId = CommonUtil.generateId();
        this.userId = userId;
        this.contactId = contactId;
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
