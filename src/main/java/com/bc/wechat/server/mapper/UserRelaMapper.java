package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.entity.UserRela;

import java.util.List;
import java.util.Map;

/**
 * 用户关系dao
 *
 * @author zhou
 */
public interface UserRelaMapper {

    /**
     * 新增用户关系
     *
     * @param userRela 用户关系
     */
    void addUserRela(UserRela userRela);

    /**
     * 根据用户ID和好友ID判断两人是否有好友关系
     *
     * @param paramMap 参数map
     * @return 用户关系列表
     */
    List<UserRela> getUserRelaListByUserIdAndFriendId(Map<String, String> paramMap);

    /**
     * 获取好友列表
     *
     * @param userId 用户ID
     * @return 好友列表
     */
    List<User> getFriendList(String userId);

    /**
     * 删除好友
     *
     * @param paramMap 参数map
     *                 包含用户ID和好友ID
     */
    void deleteFriend(Map<String, String> paramMap);
}
