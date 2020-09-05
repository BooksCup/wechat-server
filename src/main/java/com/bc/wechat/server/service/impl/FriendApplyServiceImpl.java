package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.FriendApply;
import com.bc.wechat.server.entity.UserRela;
import com.bc.wechat.server.mapper.FriendApplyMapper;
import com.bc.wechat.server.mapper.UserRelaMapper;
import com.bc.wechat.server.service.FriendApplyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 好友申请业务类实现类
 *
 * @author zhou
 */
@Service("friendApplyService")
public class FriendApplyServiceImpl implements FriendApplyService {

    @Resource
    private FriendApplyMapper friendApplyMapper;

    @Resource
    private UserRelaMapper userRelaMapper;

    /**
     * 新增好友申请
     *
     * @param friendApply 好友申请
     */
    @Override
    public void addFriendApply(FriendApply friendApply) {
        friendApplyMapper.addFriendApply(friendApply);
    }

    /**
     * 接受好友申请
     *
     * @param applyId 申请ID
     */
    @Override
    public void acceptFriendApply(String applyId) {
        friendApplyMapper.acceptFriendApply(applyId);
    }

    /**
     * 根据申请ID获取好友申请
     *
     * @param applyId 申请ID
     * @return 好友申请
     */
    @Override
    public FriendApply getFriendApplyById(String applyId) {
        return friendApplyMapper.getFriendApplyById(applyId);
    }

    /**
     * 交朋友
     *
     * @param fromUserId    申请人用户ID
     * @param toUserId      接收人用户ID
     * @param relaRemark    好友备注
     * @param relaAuth      好友朋友权限 "0":聊天、朋友圈、微信运动  "1":仅聊天
     * @param relaNotSeeMe  朋友圈和视频动态 "0":可以看我 "1":不让他看我
     * @param relaNotSeeHim 朋友圈和视频动态 "0":可以看他 "1":不看他
     */
    @Override
    public void makeFriends(String fromUserId, String toUserId, String relaRemark,
                            String relaAuth, String relaNotSeeMe, String relaNotSeeHim) {
        // 更新申请者userRela
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", fromUserId);
        paramMap.put("friendId", toUserId);
        List<UserRela> userRelaList = userRelaMapper.getUserRelaListByUserIdAndContactId(paramMap);
        if (CollectionUtils.isEmpty(userRelaList)) {
            UserRela userRela = new UserRela(fromUserId, toUserId);
            userRela.setRelaStatus(Constant.RELA_STATUS_FRIEND);
            userRelaMapper.addUserRela(userRela);
        } else {
            UserRela userRela = userRelaList.get(0);
            userRela.setRelaStatus(Constant.RELA_STATUS_FRIEND);
            userRelaMapper.updateUserRela(userRela);
        }

        paramMap.clear();
        paramMap.put("fromUserId", toUserId);
        paramMap.put("toUserId", fromUserId);
        List<UserRela> friendUserRelaList = userRelaMapper.getUserRelaListByUserIdAndContactId(paramMap);
        if (CollectionUtils.isEmpty(friendUserRelaList)) {
            UserRela userRela = new UserRela(toUserId, fromUserId, relaRemark,
                    relaAuth, relaNotSeeMe, relaNotSeeHim);
            userRela.setRelaStatus(Constant.RELA_STATUS_FRIEND);
            userRelaMapper.addUserRela(userRela);
        } else {
            UserRela userRela = userRelaList.get(0);
            userRela.setRelaStatus(Constant.RELA_STATUS_FRIEND);
            userRelaMapper.updateUserRela(userRela);
        }

    }
}
