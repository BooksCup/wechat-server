package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.entity.UserRela;
import com.bc.wechat.server.mapper.UserRelaMapper;
import com.bc.wechat.server.service.UserRelaService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户关系业务类实现类
 *
 * @author zhou
 */
@Service("userRelaService")
public class UserRelaServiceImpl implements UserRelaService {

    @Resource
    private UserRelaMapper userRelaMapper;

    /**
     * 新增用户关系
     *
     * @param userRela 用户关系
     */
    @Override
    public void addUserRela(UserRela userRela) {
        userRelaMapper.addUserRela(userRela);
    }

    /**
     * 修改用户关系
     *
     * @param userRela 用户关系
     */
    @Override
    public void updateUserRela(UserRela userRela) {
        userRelaMapper.updateUserRela(userRela);
    }

    /**
     * 检查是否好友关系
     *
     * @param paramMap 参数map
     * @return true:是  false:否
     */
    @Override
    public boolean checkIsFriend(Map<String, String> paramMap) {
        List<UserRela> userRelaList = userRelaMapper.getUserRelaListByUserIdAndFriendId(paramMap);
        if (CollectionUtils.isEmpty(userRelaList)) {
            return false;
        }
        return true;
    }

    /**
     * 获取用户关系列表
     *
     * @param paramMap 参数map
     * @return 用户关系列表
     */
    @Override
    public List<UserRela> getUserRelaListByUserIdAndFriendId(Map<String, String> paramMap) {
        return userRelaMapper.getUserRelaListByUserIdAndFriendId(paramMap);
    }

    /**
     * 获取好友列表
     *
     * @param userId 用户ID
     * @return 好友列表
     */
    @Override
    public List<User> getFriendList(String userId) {
        return userRelaMapper.getFriendList(userId);
    }

    /**
     * 删除好友
     *
     * @param paramMap 参数map
     *                 包含用户ID和好友ID
     */
    @Override
    public void deleteFriend(Map<String, String> paramMap) {
        userRelaMapper.deleteFriend(paramMap);
    }

    /**
     * 通过好友申请的方式建立初始化的单向用户关系
     * 主要初始化备注信息，朋友权限，朋友圈和视频动态
     *
     * @param fromUserId    用户ID
     * @param toUserId      好友ID
     * @param relaRemark    好友备注
     * @param relaAuth      好友朋友权限 "0":聊天、朋友圈、微信运动  "1":仅聊天
     * @param relaNotSeeMe  朋友圈和视频动态 "0":可以看我 "1":不让他看我
     * @param relaNotSeeHim 朋友圈和视频动态 "0":可以看他 "1":不看他
     */
    @Override
    public void addSingleUserRelaByFriendApply(String fromUserId, String toUserId,
                                               String relaRemark, String relaAuth,
                                               String relaNotSeeMe, String relaNotSeeHim) {
        Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
        paramMap.put("userId", fromUserId);
        paramMap.put("friendId", toUserId);
        List<UserRela> userRelaList = userRelaMapper.getUserRelaListByUserIdAndFriendId(paramMap);
        if (CollectionUtils.isEmpty(userRelaList)) {
            UserRela userRela = new UserRela(fromUserId, toUserId, relaRemark, relaAuth, relaNotSeeMe, relaNotSeeHim);
            userRela.setRelaStatus(Constant.RELA_STATUS_STRANGER);
            userRelaMapper.addUserRela(userRela);
        } else {
            UserRela userRela = userRelaList.get(0);
            userRela.setRelaFriendRemark(relaRemark);
            userRela.setRelaAuth(relaAuth);
            userRela.setRelaNotSeeMe(relaNotSeeMe);
            userRela.setRelaNotSeeHim(relaNotSeeHim);

            userRelaMapper.updateUserRelaByFriendApply(userRela);
        }
    }

    /**
     * 设置或取消星标朋友
     *
     * @param paramMap 参数map
     * @return ResponseEntity
     */
    @Override
    public void updateUserStarFriend(Map<String, String> paramMap) {
        userRelaMapper.updateUserStarFriend(paramMap);
    }
}
