package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.User;

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
    List<User> getUserByLogin(Map<String, String> paramMap);

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
     * 修改用户头像
     *
     * @param paramMap 参数map
     */
    void updateUserAvatar(Map<String, String> paramMap);

    /**
     * 修改用户签名
     *
     * @param paramMap 参数map
     */
    void updateUserSign(Map<String, String> paramMap);

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
    void updateUserLastestCirclePhotos(Map<String, String> paramMap);

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
     * 获取所有用户
     *
     * @return 所有用户
     */
    List<User> getAllUserList();
}
