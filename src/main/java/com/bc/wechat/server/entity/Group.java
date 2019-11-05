package com.bc.wechat.server.entity;


import cn.jmessage.api.group.GroupInfoResult;

import java.util.List;


/**
 * 群组
 * @author zhou
 */
public class Group {

    private String id;

    /**
     * 群主userId
     */
    private String owner;

    /**
     * 群组名字
     * 支持的字符：全部，包括 Emoji。
     */
    private String name;

    /**
     * 群描述
     * 支持的字符：全部，包括 Emoji。
     */
    private String desc;

    /**
     * 成员用户
     */
    private List<User> userList;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 极光
     */
    private Long jId;


    public Group() {
    }

    public Group(String owner, String name, String desc) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getjId() {
        return jId;
    }

    public void setjId(Long jId) {
        this.jId = jId;
    }


    public Group getGroupFromJIMGroupInfoResult(GroupInfoResult groupInfoResult) {
        Group group = new Group();
        group.setjId(groupInfoResult.getGid());
        group.setName(groupInfoResult.getName());
        group.setDesc(groupInfoResult.getDesc());
        group.setCreateTime(groupInfoResult.getCtime());
        group.setUpdateTime(groupInfoResult.getMtime());
        return group;
    }
}
