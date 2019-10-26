package com.bc.wechat.server.entity;

import java.io.Serializable;


/**
 * 消息
 *
 * @author zhou
 */
public class Message implements Serializable {
    private String id;

    private String targetType;
    private String fromType;
    private String msgType;
    private String fromId;
    private String targetId;
    private String body;
    private String createTime;

    /**
     * 极光返回的结果
     */
    private Long jId;
    private Long jCreateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getjId() {
        return jId;
    }

    public void setjId(Long jId) {
        this.jId = jId;
    }

    public Long getjCreateTime() {
        return jCreateTime;
    }

    public void setjCreateTime(Long jCreateTime) {
        this.jCreateTime = jCreateTime;
    }

}
