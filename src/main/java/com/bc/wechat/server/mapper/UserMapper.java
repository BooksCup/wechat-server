package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.entity.UserContactTag;

import java.util.List;
import java.util.Map;

/**
 * 用户dao
 *
 * @author zhou
 */
public interface UserMapper {

    /**
     * 通过用户名和密码获取用户列表(用于登录)
     *
     * @param paramMap 参数map
     * @return 用户列表
     */
    List<User> getUserByPhoneAndPassword(Map<String, String> paramMap);

    /**
     * 新增用户
     *
     * @param user 用户
     */
    void addUser(User user);

    /**
     * 修改用户昵称
     *
     * @param paramMap 参数map
     */
    void updateUserNickName(Map<String, String> paramMap);

    /**
     * 修改用户微信号
     *
     * @param paramMap 参数map
     */
    void updateUserWxId(Map<String, String> paramMap);

    /**
     * 修改用户性别
     *
     * @param paramMap 参数map
     */
    void updateUserSex(Map<String, String> paramMap);

    /**
     * 修改用户地区
     *
     * @param paramMap 参数map
     */
    void updateUserRegion(Map<String, String> paramMap);

    /**
     * 修改用户头像
     *
     * @param paramMap 参数map
     */
    void updateUserAvatar(Map<String, String> paramMap);

    /**
     * 修改用户密码
     *
     * @param paramMap 参数map
     */
    void updateUserPassword(Map<String, String> paramMap);

    /**
     * 修改用户签名
     *
     * @param paramMap 参数map
     */
    void updateUserSign(Map<String, String> paramMap);

    /**
     * 修改用户邮箱
     *
     * @param paramMap 参数map
     */
    void updateUserEmail(Map<String, String> paramMap);

    /**
     * 修改用户QQ号
     *
     * @param paramMap 参数map
     */
    void updateUserQqId(Map<String, String> paramMap);

    /**
     * 修改用户二维码
     *
     * @param paramMap 参数map
     */
    void updateUserQrCode(Map<String, String> paramMap);

    /**
     * 修改朋友圈最新图片
     *
     * @param paramMap 参数map
     */
    void updateUserLatestMomentsPhotos(Map<String, String> paramMap);

    /**
     * 修改朋友圈封面
     *
     * @param paramMap 修改朋友圈封面
     */
    void updateUserMomentsCover(Map<String, String> paramMap);

    /**
     * 修改用户最后一次登录时间
     *
     * @param userId 用户ID
     */
    void updateUserLastLoginTime(String userId);

    /**
     * 通过关键字搜索用户
     *
     * @param keyword 关键字  手机号/微信号
     * @return 用户列表
     */
    List<User> getUserByKeyword(String keyword);

    /**
     * 根据用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户列表
     */
    List<User> getUserByUserId(String userId);

    /**
     * 根据用户手机号获取用户
     *
     * @param userPhone 用户手机号
     * @return 用户列表
     */
    List<User> getUserByUserPhone(String userPhone);

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    List<User> getAllUserList();

    /**
     * 通过手机号列表获取用户列表
     *
     * @param phoneList 手机列表
     * @return 用户列表
     */
    List<User> getUserListByPhoneList(List<String> phoneList);

    /**
     * 保存搜索历史
     *
     * @param paramMap
     */
    void saveSearchHistory(Map<String, String> paramMap);

    /**
     * 根据用户ID删除用户标签
     *
     * @param userId 用户ID
     */
    void deleteUserContactTagsByUserId(String userId);

    /**
     * 批量保存用户标签
     *
     * @param userContactTagList 用户标签列表
     */
    void batchSaveUserContactTags(List<UserContactTag> userContactTagList);

    /**
     * 保存用户对好友贴的标签
     *
     * @param paramMap 参数map
     */
    void saveUserTags(Map<String, String> paramMap);

    /**
     * 根据ID删除用户
     *
     * @param userId 用户ID
     */
    void deleteUserById(String userId);

    /**
     * 获取用户列表(admin端)
     *
     * @param paramMap 参数map
     * @return 用户列表
     */
    List<User> getUserListForAdmin(Map<String, String> paramMap);
}
