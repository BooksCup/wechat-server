package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.mapper.FriendsCircleMapper;
import com.bc.wechat.server.service.FriendsCircleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 朋友圈业务类实现类
 *
 * @author zhou
 */
@Service("friendsCircleService")
public class FriendsCircleServiceImpl implements FriendsCircleService {

    @Resource
    private FriendsCircleMapper friendsCircleMapper;

    @Override
    public List<FriendsCircle> getFriendsCircleListByUserId(String userId) {
        return friendsCircleMapper.getFriendsCircleListByUserId(userId);
    }
}
