package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.FriendApply;
import com.bc.wechat.server.mapper.FriendApplyMapper;
import com.bc.wechat.server.service.FriendApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 好友申请业务类实现类
 *
 * @author zhou
 */
@Service("friendApplyService")
public class FriendApplyServiceImpl implements FriendApplyService {

    @Resource
    private FriendApplyMapper friendApplyMapper;

    /**
     * 新增好友申请
     *
     * @param friendApply 好友申请
     */
    @Override
    public void addFriendApply(FriendApply friendApply) {
        friendApplyMapper.addFriendApply(friendApply);
    }
}
