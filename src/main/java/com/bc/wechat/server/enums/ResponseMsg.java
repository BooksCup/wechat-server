package com.bc.wechat.server.enums;

/**
 * 响应消息
 *
 * @author zhou
 */
public enum ResponseMsg {
    /**
     * wechat-server接口返回信息
     */
    LOGIN_SUCCESS("登录成功"),
    LOGIN_ERROR("登录失败"),
    REGISTER_SUCCESS("注册成功"),
    REGISTER_ERROR("注册失败"),
    UPDATE_USER_NICK_NAME_SUCCESS("修改昵称成功"),
    UPDATE_USER_NICK_NAME_ERROR("修改昵称失败"),
    UPDATE_USER_WX_ID_SUCCESS("修改微信号成功"),
    UPDATE_USER_WX_ID_ERROR("修改微信号失败"),
    ADD_FRIEND_APPLY_SUCCESS("好友申请发送成功"),
    ADD_FRIEND_APPLY_ERROR("好友申请发送失败"),;
    private final String reasonPhrase;

    ResponseMsg(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String value() {
        return reasonPhrase;
    }
}
