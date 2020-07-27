package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

import java.util.List;

/**
 * 朋友圈
 *
 * @author zhou
 */
public class Moments {

    private String momentsId;
    private String userId;
    private String userNickName;
    private String userAvatar;
    private String momentsContent;
    private String momentsPhotos;
    private String createTime;
    private Long timestamp;

    /**
     * 点赞用户列表
     */
    private List<User> likeUserList;

    /**
     * 评论列表
     */
    private List<FriendsCircleComment> friendsCircleCommentList;

    public Moments() {

    }

    public Moments(String userId, String momentsContent, String momentsPhotos) {
        this.momentsId = CommonUtil.generateId();
        this.userId = userId;
        this.momentsContent = momentsContent;
        this.momentsPhotos = momentsPhotos;
        this.createTime = CommonUtil.now();
        this.timestamp = System.currentTimeMillis();

    }

    public String getMomentsId() {
        return momentsId;
    }

    public void setMomentsId(String momentsId) {
        this.momentsId = momentsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getMomentsContent() {
        return momentsContent;
    }

    public void setMomentsContent(String momentsContent) {
        this.momentsContent = momentsContent;
    }

    public String getMomentsPhotos() {
        return momentsPhotos;
    }

    public void setMomentsPhotos(String momentsPhotos) {
        this.momentsPhotos = momentsPhotos;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<User> getLikeUserList() {
        return likeUserList;
    }

    public void setLikeUserList(List<User> likeUserList) {
        this.likeUserList = likeUserList;
    }

    public List<FriendsCircleComment> getFriendsCircleCommentList() {
        return friendsCircleCommentList;
    }

    public void setFriendsCircleCommentList(List<FriendsCircleComment> friendsCircleCommentList) {
        this.friendsCircleCommentList = friendsCircleCommentList;
    }
}
