package com.bc.wechat.server.entity;


import com.bc.wechat.server.utils.CommonUtil;

import java.util.UUID;

/**
 * 群组成员
 *
 * @author zhou
 */
public class GroupMembers {
    private String id;
    private String groupId;
    private String userId;
    private String createTime;

    private Integer isOwner;

    public GroupMembers() {

    }

    public GroupMembers(String groupId, String userId, Integer isOwner) {
        this.id = CommonUtil.generateId();
        this.groupId = groupId;
        this.userId = userId;
        this.isOwner = isOwner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Integer isOwner) {
        this.isOwner = isOwner;
    }
}
