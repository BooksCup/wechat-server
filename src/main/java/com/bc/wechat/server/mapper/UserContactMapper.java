package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.entity.UserContact;

import java.util.List;
import java.util.Map;

/**
 * 用户联系人(通讯录)
 *
 * @author zhou
 */
public interface UserContactMapper {

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
     *                 包含用户ID和联系人用户ID
     */
    void deleteContact(Map<String, String> paramMap);

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
     */
    void setContactStarred(Map<String, String> paramMap);

    /**
     * 设置或取消加入黑名单
     *
     * @param paramMap 参数map
     */
    void setContactBlocked(Map<String, String> paramMap);
}
