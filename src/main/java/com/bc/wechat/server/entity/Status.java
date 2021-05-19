package com.bc.wechat.server.entity;

/**
 * 状态
 *
 * @author zhou
 */
public class Status {

    private String statusId;

    /**
     * 状态名
     */
    private String name;

    /**
     * 状态icon
     */
    private String icon;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}