package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.entity.UserContact;

import java.util.List;
import java.util.Map;

/**
 * 用户联系人(通讯录)
 *
 * @author zhou
 */
public interface UserContactService {

    /**
     * 新增用户联系人
     *
     * @param userContact 用户联系人
     */
    void addUserContact(UserContact userContact);

    /**
     * 修改用户联系人
     *
     * @param userContact 用户联系人
     */
    void updateUserContact(UserContact userContact);

    /**
     * 设置朋友权限
     *
     * @param userContact 用户联系人
     */
    void saveContactPrivacy(UserContact userContact);

    /**
     * 修改朋友权限
     *
     * @param userContact 用户联系人
     */
    void updateContactPrivacy(UserContact userContact);

    /**
     * 检查是否好友关系
     *
     * @param paramMap 参数map
     * @return true:是  false:否
     */
    boolean checkIsFriend(Map<String, String> paramMap);

    /**
     * 根据用户ID和联系人用户ID获取用户联系人列表
     *
     * @param paramMap 参数map
     * @return 用户联系人列表
     */
    List<UserContact> getUserContactListByUserIdAndContactUserId(Map<String, String> paramMap);

    /**
     * 获取联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    List<User> getContactList(String userId);

    /**
     * 删除联系人
     *
     * @param paramMap 参数map
     *                 包含用户ID和联系人ID
     */
    void deleteContact(Map<String, String> paramMap);

    /**
     * 通过好友申请的方式建立初始化的单向用户关系
     * 主要初始化备注信息，朋友权限，朋友圈和视频动态
     *
     * @param fromUserId   用户ID
     * @param toUserId     好友ID
     * @param contactFrom  好友来源
     * @param contactAlias 好友备注
     * @param privacy      好友朋友权限 "0":聊天、朋友圈、微信运动  "1":仅聊天
     * @param hideMyPosts  朋友圈和视频动态 "0":可以看我 "1":不让他看我
     * @param hideHisPosts 朋友圈和视频动态 "0":可以看他 "1":不看他
     */
    void addSingleUserContactByFriendApply(String fromUserId, String toUserId,
                                           String contactFrom,
                                           String contactAlias, String privacy,
                                           String hideMyPosts, String hideHisPosts);

    /**
     * 新增联系人标签
     *
     * @param userContact 用户联系人(带标签)
     */
    void addUserContactTags(UserContact userContact);

    /**
     * 修改联系人标签
     *
     * @param userContact 用户联系人(带标签)
     */
    void updateUserContactTags(UserContact userContact);

    /**
     * 设置或取消星标朋友
     *
     * @param paramMap 参数map
     * @return ResponseEntity
     */
    void setContactStarred(Map<String, String> paramMap);

    /**
     * 设置或取消加入黑名单
     *
     * @param paramMap 参数map
     */
    void setContactBlocked(Map<String, String> paramMap);
}
