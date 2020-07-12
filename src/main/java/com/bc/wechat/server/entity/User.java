package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 *
 * @author zhou
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String userWxId;

    /**
     * 用户类型
     * "REG": 普通注册用户
     * "WEIXIN": 微信团队
     * "FILEHELPER": 文件传输助手
     */
    private String userType;
    private String userNickName;
    private String userPhone;
    private String userPassword;
    private String userImPassword;
    private String userAvatar;
    private String userHeader;
    private String userSex;
    private String userRegion;
    private String userSign;
    private String userEmail;
    private String userIsEmailLinked;
    private String userQqId;
    private String userQqPassword;
    private String userIsQqLinked;
    private String userQrCode;
    private String isFriend;
    private List<User> contactList;
    private String isOwner;

    private String userWxIdModifyFlag;
    private String userLastestMomentsPhotos;

    /**
     * 联系人来源
     */
    private String userContactFrom;

    /**
     * 联系人相关
     */
    private String userContactAlias;
    private String userContactTags;
    private String userContactMobiles;
    private String userContactDesc;

    /**
     * 联系人权限相关
     */
    private String userContactPrivacy;
    private String userContactHideMyPosts;
    private String userContactHideHisPosts;

    private String isStarred;

    private String userLastLoginTime;
    private String userCreateTime;

    public User() {

    }

    public User(String userNickName, String userPhone, String userPassword) {
        this.userId = CommonUtil.generateId();
        this.userNickName = userNickName;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserWxId() {
        return userWxId;
    }

    public void setUserWxId(String userWxId) {
        this.userWxId = userWxId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserImPassword() {
        return userImPassword;
    }

    public void setUserImPassword(String userImPassword) {
        this.userImPassword = userImPassword;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIsEmailLinked() {
        return userIsEmailLinked;
    }

    public void setUserIsEmailLinked(String userIsEmailLinked) {
        this.userIsEmailLinked = userIsEmailLinked;
    }

    public String getUserQqId() {
        return userQqId;
    }

    public void setUserQqId(String userQqId) {
        this.userQqId = userQqId;
    }

    public String getUserQqPassword() {
        return userQqPassword;
    }

    public void setUserQqPassword(String userQqPassword) {
        this.userQqPassword = userQqPassword;
    }

    public String getUserIsQqLinked() {
        return userIsQqLinked;
    }

    public void setUserIsQqLinked(String userIsQqLinked) {
        this.userIsQqLinked = userIsQqLinked;
    }

    public String getUserQrCode() {
        return userQrCode;
    }

    public void setUserQrCode(String userQrCode) {
        this.userQrCode = userQrCode;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public List<User> getContactList() {
        return contactList;
    }

    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public String getUserWxIdModifyFlag() {
        return userWxIdModifyFlag;
    }

    public void setUserWxIdModifyFlag(String userWxIdModifyFlag) {
        this.userWxIdModifyFlag = userWxIdModifyFlag;
    }

    public String getUserLastestMomentsPhotos() {
        return userLastestMomentsPhotos;
    }

    public void setUserLastestMomentsPhotos(String userLastestMomentsPhotos) {
        this.userLastestMomentsPhotos = userLastestMomentsPhotos;
    }

    public String getUserContactFrom() {
        return userContactFrom;
    }

    public void setUserContactFrom(String userContactFrom) {
        this.userContactFrom = userContactFrom;
    }

    public String getUserContactAlias() {
        return userContactAlias;
    }

    public void setUserContactAlias(String userContactAlias) {
        this.userContactAlias = userContactAlias;
    }

    public String getUserContactTags() {
        return userContactTags;
    }

    public void setUserContactTags(String userContactTags) {
        this.userContactTags = userContactTags;
    }

    public String getUserContactMobiles() {
        return userContactMobiles;
    }

    public void setUserContactMobiles(String userContactMobiles) {
        this.userContactMobiles = userContactMobiles;
    }

    public String getUserContactDesc() {
        return userContactDesc;
    }

    public void setUserContactDesc(String userContactDesc) {
        this.userContactDesc = userContactDesc;
    }

    public String getUserContactPrivacy() {
        return userContactPrivacy;
    }

    public void setUserContactPrivacy(String userContactPrivacy) {
        this.userContactPrivacy = userContactPrivacy;
    }

    public String getUserContactHideMyPosts() {
        return userContactHideMyPosts;
    }

    public void setUserContactHideMyPosts(String userContactHideMyPosts) {
        this.userContactHideMyPosts = userContactHideMyPosts;
    }

    public String getUserContactHideHisPosts() {
        return userContactHideHisPosts;
    }

    public void setUserContactHideHisPosts(String userContactHideHisPosts) {
        this.userContactHideHisPosts = userContactHideHisPosts;
    }

    public String getIsStarred() {
        return isStarred;
    }

    public void setIsStarred(String isStarred) {
        this.isStarred = isStarred;
    }

    public String getUserLastLoginTime() {
        return userLastLoginTime;
    }

    public void setUserLastLoginTime(String userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
    }

    public String getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
    }
}
