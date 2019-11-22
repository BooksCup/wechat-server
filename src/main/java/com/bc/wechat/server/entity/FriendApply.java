package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 好友申请
 *
 * @author zhou
 */
public class FriendApply {
    private String applyId;
    private String fromUserId;
    private String toUserId;
    private String applyRemark;
    private String createTime;

    private String fromUserNickName;
    private String fromUserAvatar;
    private String fromUserSex;
    private String fromUserSign;
    private String fromUserLastestCirclePhotos;

    private Long timeStamp;

    public FriendApply() {

    }

    public FriendApply(String fromUserId, String toUserId, String applyRemark) {
        this.applyId = CommonUtil.generateId();
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.applyRemark = applyRemark;
        this.timeStamp = System.currentTimeMillis();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFromUserNickName() {
        return fromUserNickName;
    }

    public void setFromUserNickName(String fromUserNickName) {
        this.fromUserNickName = fromUserNickName;
    }

    public String getFromUserAvatar() {
        return fromUserAvatar;
    }

    public void setFromUserAvatar(String fromUserAvatar) {
        this.fromUserAvatar = fromUserAvatar;
    }

    public String getFromUserSex() {
        return fromUserSex;
    }

    public void setFromUserSex(String fromUserSex) {
        this.fromUserSex = fromUserSex;
    }

    public String getFromUserSign() {
        return fromUserSign;
    }

    public void setFromUserSign(String fromUserSign) {
        this.fromUserSign = fromUserSign;
    }

    public String getFromUserLastestCirclePhotos() {
        return fromUserLastestCirclePhotos;
    }

    public void setFromUserLastestCirclePhotos(String fromUserLastestCirclePhotos) {
        this.fromUserLastestCirclePhotos = fromUserLastestCirclePhotos;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
