package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.entity.UserContact;
import com.bc.wechat.server.mapper.UserContactMapper;
import com.bc.wechat.server.service.UserContactService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户联系人(通讯录)
 *
 * @author zhou
 */
@Service("userContactService")
public class UserContactServiceImpl implements UserContactService {

    @Resource
    private UserContactMapper userContactMapper;

    /**
     * 新增用户联系人
     *
     * @param userContact 用户联系人
     */
    @Override
    public void addUserContact(UserContact userContact) {
        userContactMapper.addUserContact(userContact);
    }

    /**
     * 修改用户联系人
     *
     * @param userContact 用户联系人
     */
    @Override
    public void updateUserContact(UserContact userContact) {
        userContactMapper.updateUserContact(userContact);
    }

    /**
     * 设置朋友权限
     *
     * @param userContact 用户联系人
     */
    @Override
    public void saveContactPrivacy(UserContact userContact) {
        userContactMapper.saveContactPrivacy(userContact);
    }

    /**
     * 修改朋友权限
     *
     * @param userContact 用户联系人
     */
    @Override
    public void updateContactPrivacy(UserContact userContact) {
        userContactMapper.updateContactPrivacy(userContact);
    }

    /**
     * 检查是否好友关系
     *
     * @param paramMap 参数map
     * @return true:是  false:否
     */
    @Override
    public boolean checkIsFriend(Map<String, String> paramMap) {
        List<UserContact> userContactList
                = userContactMapper.getUserContactListByUserIdAndContactUserId(paramMap);
        if (CollectionUtils.isEmpty(userContactList)) {
            return false;
        }
        return true;
    }

    /**
     * 根据用户ID和联系人用户ID获取用户联系人列表
     *
     * @param paramMap 参数map
     * @return 用户联系人列表
     */
    @Override
    public List<UserContact> getUserContactListByUserIdAndContactUserId(Map<String, String> paramMap) {
        return userContactMapper.getUserContactListByUserIdAndContactUserId(paramMap);
    }

    /**
     * 获取联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    @Override
    public List<User> getContactList(String userId) {
        return userContactMapper.getContactList(userId);
    }

    /**
     * 删除好友
     *
     * @param paramMap 参数map
     *                 包含用户ID和好友ID
     */
    @Override
    public void deleteContact(Map<String, String> paramMap) {
        userContactMapper.deleteContact(paramMap);
    }

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
    @Override
    public void addSingleUserContactByFriendApply(String fromUserId, String toUserId,
                                                  String contactFrom,
                                                  String contactAlias, String privacy,
                                                  String hideMyPosts, String hideHisPosts) {
        Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
        paramMap.put("userId", fromUserId);
        paramMap.put("contactUserId", toUserId);
        List<UserContact> userContactList = userContactMapper.getUserContactListByUserIdAndContactUserId(paramMap);
        if (CollectionUtils.isEmpty(userContactList)) {
            UserContact userContact = new UserContact(fromUserId, toUserId, contactAlias, privacy, hideMyPosts, hideHisPosts);
            userContact.setStatus(Constant.RELA_STATUS_STRANGER);
            userContact.setContactFrom(contactFrom);
            userContactMapper.addUserContact(userContact);
        } else {
            UserContact userContact = userContactList.get(0);
            if (!StringUtils.isEmpty(contactFrom)) {
                userContact.setContactFrom(contactFrom);
            }
            userContact.setContactAlias(contactAlias);
            userContact.setPrivacy(privacy);
            userContact.setHideMyPosts(hideMyPosts);
            userContact.setHideHisPosts(hideHisPosts);
            userContactMapper.updateContactPrivacy(userContact);
        }
    }

    /**
     * 新增联系人标签
     *
     * @param userContact 用户联系人(带标签)
     */
    @Override
    public void addUserContactTags(UserContact userContact) {
        userContactMapper.addUserContactTags(userContact);
    }

    /**
     * 修改联系人标签
     *
     * @param userContact 用户联系人(带标签)
     */
    @Override
    public void updateUserContactTags(UserContact userContact) {
        userContactMapper.updateUserContactTags(userContact);
    }

    /**
     * 设置或取消星标朋友
     *
     * @param paramMap 参数map
     */
    @Override
    public void setContactStarred(Map<String, String> paramMap) {
        userContactMapper.setContactStarred(paramMap);
    }

    /**
     * 设置或取消加入黑名单
     *
     * @param paramMap 参数map
     */
    @Override
    public void setContactBlocked(Map<String, String> paramMap) {
        userContactMapper.setContactBlocked(paramMap);
    }

}