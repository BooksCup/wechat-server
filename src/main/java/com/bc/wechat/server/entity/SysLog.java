package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 系统日志
 *
 * @author zhou
 */
public class SysLog {
    private String id;
    private String type;
    private String userId;
    private String content;
    private String createTime;

    public SysLog() {

    }

    public SysLog(String type, String content) {
        this.id = CommonUtil.generateId();
        this.type = type;
        this.content = content;
    }


    public SysLog(String type, String userId, String content) {
        this.id = CommonUtil.generateId();
        this.type = type;
        this.userId = userId;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
