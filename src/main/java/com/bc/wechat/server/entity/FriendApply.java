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

    public FriendApply() {

    }

    public FriendApply(String fromUserId, String toUserId, String applyRemark) {
        this.applyId = CommonUtil.generateId();
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.applyRemark = applyRemark;
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
}
