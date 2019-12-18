package com.bc.wechat.server.enums;

/**
 * 返回消息
 *
 * @author zhou
 */
public enum ResponseContent {
    /**
     * wechat-server接口返回信息
     */
    USER_EXISTS("USER_EXISTS", "用户已存在"),

    ADD_USER_ID_LIST_ILLEGAL("ADD_USER_ID_LIST_ILLEGAL", "添加群组成员ID格式不对"),
    REMOVE_USER_ID_LIST_ILLEGAL("REMOVE_USER_ID_LIST_ILLEGAL", "移除群组成员ID格式不对"),

    ADD_OR_REMOVE_MEMBERS_SUCCESS("ADD_OR_REMOVE_MEMBERS_SUCCESS", "添加或删除群组成员失败"),
    ADD_OR_REMOVE_MEMBERS_ERROR("ADD_OR_REMOVE_MEMBERS_ERROR", "添加或删除群组成员失败"),

    ADD_FRIENDS_CIRCLE_SUCCESS("ADD_FRIENDS_CIRCLE_SUCCESS", "发朋友圈成功"),
    ADD_FRIENDS_CIRCLE_ERROR("ADD_FRIENDS_CIRCLE_ERROR", "发朋友圈失败"),

    LIKE_FRIENDS_CIRCLE_SUCCESS("LIKE_FRIENDS_CIRCLE_SUCCESS", "朋友圈点赞成功"),
    LIKE_FRIENDS_CIRCLE_ERROR("LIKE_FRIENDS_CIRCLE_ERROR", "朋友圈点赞失败"),

    UNLIKE_FRIENDS_CIRCLE_SUCCESS("UNLIKE_FRIENDS_CIRCLE_SUCCESS", "朋友圈取消点赞成功"),
    UNLIKE_FRIENDS_CIRCLE_ERROR("UNLIKE_FRIENDS_CIRCLE_ERROR", "朋友圈取消点赞失败"),;

    ResponseContent(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    private String responseCode;
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
