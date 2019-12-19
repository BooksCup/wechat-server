package com.bc.wechat.server.enums;

/**
 * 返回消息
 *
 * @author zhou
 */
public enum ResponseMsg {
    /**
     * wechat-server接口返回信息
     */
    USER_EXISTS("USER_EXISTS", "用户已存在"),

    UPDATE_USER_NICK_NAME_SUCCESS("UPDATE_USER_NICK_NAME_SUCCESS", "修改昵称成功"),
    UPDATE_USER_NICK_NAME_ERROR("UPDATE_USER_NICK_NAME_ERROR", "修改昵称失败"),

    UPDATE_USER_WX_ID_SUCCESS("UPDATE_USER_WX_ID_SUCCESS", "修改微信号成功"),
    UPDATE_USER_WX_ID_ERROR("UPDATE_USER_WX_ID_ERROR", "修改微信号失败"),

    UPDATE_USER_SEX_SUCCESS("UPDATE_USER_SEX_SUCCESS", "修改性别成功"),
    UPDATE_USER_SEX_ERROR("UPDATE_USER_SEX_ERROR", "修改性别失败"),

    UPDATE_USER_AVATAR_SUCCESS("UPDATE_USER_AVATAR_SUCCESS", "修改头像成功"),
    UPDATE_USER_AVATAR_ERROR("UPDATE_USER_AVATAR_ERROR", "修改头像失败"),

    UPDATE_USER_SIGN_SUCCESS("UPDATE_USER_SIGN_SUCCESS", "修改签名成功"),
    UPDATE_USER_SIGN_ERROR("UPDATE_USER_SIGN_ERROR", "修改签名失败"),

    REFRESH_USER_QR_CODE_SUCCESS("REFRESH_USER_QR_CODE_SUCCESS", "刷新二维码成功"),
    REFRESH_USER_QR_CODE_ERROR("REFRESH_USER_QR_CODE_ERROR", "刷新二维码失败"),

    ADD_FRIEND_APPLY_SUCCESS("ADD_FRIEND_APPLY_SUCCESS", "好友申请发送成功"),
    ADD_FRIEND_APPLY_ERROR("ADD_FRIEND_APPLY_ERROR", "好友申请发送失败"),

    ACCEPT_FRIEND_APPLY_SUCCESS("ACCEPT_FRIEND_APPLY_SUCCESS", "接受好友申请成功"),
    ACCEPT_FRIEND_APPLY_ERROR("ACCEPT_FRIEND_APPLY_ERROR", "接受好友申请失败"),

    UPDATE_GROUP_NAME_SUCCESS("UPDATE_GROUP_NAME_SUCCESS", "修改群名成功"),
    UPDATE_GROUP_NAME_ERROR("UPDATE_GROUP_NAME_ERROR", "修改群名失败"),

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

    ResponseMsg(String responseCode, String responseMessage) {
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
