package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户业务类接口
 *
 * @author zhou
 */
public interface UserService {

    /**
     * 通过登录获取用户列表
     *
     * @param loginType        登录类型
     *                         手机号密码登录
     *                         手机号验证码登录
     *                         其他账号(微信号/QQ/邮箱登录)密码登录
     * @param phone            手机号
     * @param password         密码
     * @param verificationCode 验证码
     * @param otherAccount     其他账号(微信号/QQ/邮箱登录)
     * @return 用户列表
     */
    List<User> getUserByLogin(String loginType, String phone, String password, String verificationCode, String otherAccount);

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
     * 修改朋友圈最新图片
     *
     * @param paramMap 参数map
     */
    void updateUserLastestMomentsPhotos(Map<String, String> paramMap);

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
     * @return 用户
     */
    User getUserByUserId(String userId);

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    List<User> getAllUserList();

    /**
     * 根据用户手机号检查用户是否存在
     *
     * @param userPhone 用户手机号
     * @return true: 存在   false: 不存在
     */
    boolean checkUserExistsByUserPhone(String userPhone);

    /**
     * 通过手机号列表获取用户列表
     *
     * @param phoneList 手机列表
     * @return 用户列表
     */
    List<User> getUserListByPhoneList(List<String> phoneList);

    /**
     * 刷新用户二维码
     *
     * @param user 用户
     * @return true: 刷新成功  false: 刷新失败
     */
    boolean refreshUserQrCode(User user);

    /**
     * 保存搜索历史
     *
     * @param paramMap
     */
    void saveSearchHistory(Map<String, String> paramMap);

    /**
     * 根据ID删除用户
     *
     * @param userId 用户ID
     */
    void deleteUserById(String userId);

    // admin begin

    /**
     * 获取用户分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @param paramMap 参数map
     * @return 用户分页信息
     */
    PageInfo<User> getUserPageInfo(int pageNum, int pageSize, Map<String, String> paramMap);
    // admin end
}
