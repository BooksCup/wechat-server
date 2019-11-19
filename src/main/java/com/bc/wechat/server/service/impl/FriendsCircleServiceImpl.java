package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.mapper.FriendsCircleMapper;
import com.bc.wechat.server.service.FriendsCircleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 朋友圈业务类实现类
 *
 * @author zhou
 */
@Service("friendsCircleService")
public class FriendsCircleServiceImpl implements FriendsCircleService {

    @Resource
    private FriendsCircleMapper friendsCircleMapper;

    /**
     * 新增朋友圈实体
     *
     * @param friendsCircle 朋友圈实体
     */
    @Override
    public void addFriendsCircle(FriendsCircle friendsCircle){
        friendsCircleMapper.addFriendsCircle(friendsCircle);
    }


    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 朋友圈列表
     */
    @Override
    public List<FriendsCircle> getFriendsCircleListByUserId(Map<String, Object> paramMap) {
        return friendsCircleMapper.getFriendsCircleListByUserId(paramMap);
    }
}
