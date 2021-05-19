package com.bc.wechat.server.entity;

import java.util.List;

/**
 * 状态组
 *
 * @author zhou
 */
public class StatusGroup {

    private String groupId;
    private String name;
    private List<Status> statusList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

}