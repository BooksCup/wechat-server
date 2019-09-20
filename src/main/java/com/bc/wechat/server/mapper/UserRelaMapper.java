package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.UserRela;

import java.util.List;
import java.util.Map;

/**
 * 用户关系dao
 *
 * @author zhou
 */
public interface UserRelaMapper {
    void addUserRela(UserRela userRela);

    List<UserRela> getUserRelaListByUserIdAndFriendId(Map<String, String> paramMap);
}
