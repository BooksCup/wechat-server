package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.FriendsCircle;

import java.util.List;
import java.util.Map;

/**
 * 朋友圈dao
 *
 * @author zhou
 */
public interface FriendsCircleMapper {

    /**
     * 新增朋友圈实体
     *
     * @param friendsCircle 朋友圈实体
     */
    void addFriendsCircle(FriendsCircle friendsCircle);


    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 朋友圈列表
     */
    List<FriendsCircle> getFriendsCircleListByUserId(Map<String, Object> paramMap);
}
