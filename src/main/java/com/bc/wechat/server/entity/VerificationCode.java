package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 验证码
 *
 * @author zhou
 */
public class VerificationCode {
    private String id;
    private String phone;
    private String code;
    private String serviceType;
    private String createTime;
    private String expireTime;

    public VerificationCode() {

    }

    public VerificationCode(String phone, String code, String serviceType, long period) {
        this.id = CommonUtil.generateId();
        this.phone = phone;
        this.code = code;
        this.serviceType = serviceType;
        this.createTime = CommonUtil.now();
        this.expireTime = CommonUtil.getExpireTime(period);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
