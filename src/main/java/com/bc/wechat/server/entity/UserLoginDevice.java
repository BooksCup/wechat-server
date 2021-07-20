package com.bc.wechat.server.entity;

/**
 * 用户登录设备信息
 *
 * @author zhou
 */
public class UserLoginDevice {

    private String deviceId;
    private String userId;

    /**
     * 手机品牌
     */
    private String phoneBrand;

    /**
     * 手机型号
     */
    private String phoneModel;

    /**
     * 手机型号别名(用户备注)
     */
    private String phoneModelAlias;

    /**
     * 操作系统版本
     */
    private String os;

    /**
     * 分辨率
     */
    private String resolution;

    /**
     * 运营商信息
     */
    private String operator;

    private String loginTime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneModelAlias() {
        return phoneModelAlias;
    }

    public void setPhoneModelAlias(String phoneModelAlias) {
        this.phoneModelAlias = phoneModelAlias;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

}