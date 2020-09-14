package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.biz.JpushBiz;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.FriendApply;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.FriendApplyService;
import com.bc.wechat.server.service.UserRelaService;
import com.bc.wechat.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 好友申请控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/friendApplies")
public class FriendApplyController {

    private static final Logger logger = LoggerFactory.getLogger(FriendApplyController.class);

    @Resource
    private UserService userService;

    @Resource
    private FriendApplyService friendApplyService;

    @Resource
    private UserRelaService userRelaService;

    @Resource
    private JpushBiz jpushBiz;

    /**
     * 新增好友申请
     *
     * @param fromUserId       申请者用户ID
     * @param toUserId         接受者用户ID
     * @param applyRemark      申请备注
     * @param relaContactFrom  好友来源
     * @param relaContactAlias 好友备注
     * @param relaPrivacy      好友朋友权限 "0":聊天、朋友圈、微信运动  "1":仅聊天
     * @param relaHideMyPosts  朋友圈和视频动态 "0":可以看我 "1":不让他看我
     * @param relaHideHisPosts 朋友圈和视频动态 "0":可以看他 "1":不看他
     * @return
     */
    @ApiOperation(value = "新增好友申请", notes = "新增好友申请")
    @PostMapping(value = "")
    public ResponseEntity<String> addFriendApply(
            @RequestParam String fromUserId,
            @RequestParam String toUserId,
            @RequestParam String applyRemark,
            @RequestParam(required = false) String relaContactFrom,
            @RequestParam(required = false) String relaContactAlias,
            @RequestParam(required = false, defaultValue = Constant.PRIVACY_CHATS_MOMENTS_WERUN_ETC) String relaPrivacy,
            @RequestParam(required = false, defaultValue = Constant.SHOW_MY_POSTS) String relaHideMyPosts,
            @RequestParam(required = false, defaultValue = Constant.SHOW_HIS_POSTS) String relaHideHisPosts) {
        logger.info("[addFriendApply], fromUserId: " + fromUserId + ", toUserId: " + toUserId + ", relaContactFrom: " + relaContactFrom
                + ", relaContactAlias: " + relaContactAlias + ", relaPrivacy: " + relaPrivacy
                + ", relaHideMyPosts: " + relaHideMyPosts + ", relaHideHisPosts: " + relaHideHisPosts);
        ResponseEntity<String> responseEntity;
        FriendApply friendApply = new FriendApply(fromUserId, toUserId, applyRemark);
        try {
            friendApplyService.addFriendApply(friendApply);
            userRelaService.addSingleUserRelaByFriendApply(fromUserId, toUserId, relaContactFrom, relaContactAlias,
                    relaPrivacy, relaHideMyPosts, relaHideHisPosts);

            User user = userService.getUserByUserId(fromUserId);
            String alert = user.getUserNickName() + "请求加你为好友";

            Map<String, String> extras = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            extras.put("serviceType", Constant.PUSH_SERVICE_TYPE_ADD_FRIENDS_APPLY);

            friendApply.setFromUserNickName(user.getUserNickName());
            friendApply.setFromUserAvatar(user.getUserAvatar());
            friendApply.setFromUserSex(user.getUserSex());

            friendApply.setFromUserSign(user.getUserSign());
            friendApply.setFromUserLastestMomentsPhotos(user.getUserLastestMomentsPhotos());

            extras.put("friendApply", JSON.toJSONString(friendApply));

            jpushBiz.sendPush(toUserId, alert, extras);

            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIEND_APPLY_SUCCESS.getResponseCode(),
                    HttpStatus.OK);

        } catch (Exception e) {
            logger.error("[addFriendApply] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIEND_APPLY_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 接受好友申请
     * eg:接受者给申请者添加备注
     *
     * @param applyId       申请ID
     * @param relaRemark    好友备注
     * @param relaAuth      好友朋友权限 "0":聊天、朋友圈、微信运动  "1":仅聊天
     * @param relaNotSeeMe  朋友圈和视频动态 "0":可以看我 "1":不让他看我
     * @param relaNotSeeHim 朋友圈和视频动态 "0":可以看他 "1":不看他
     * @return ResponseEntity
     */
    @ApiOperation(value = "接受好友申请", notes = "接受好友申请")
    @PutMapping(value = "")
    public ResponseEntity<String> acceptFriendApply(
            @RequestParam String applyId,
            @RequestParam(required = false) String relaRemark,
            @RequestParam(required = false, defaultValue = Constant.RELA_AUTH_ALL) String relaAuth,
            @RequestParam(required = false, defaultValue = Constant.RELA_CAN_SEE_ME) String relaNotSeeMe,
            @RequestParam(required = false, defaultValue = Constant.RELA_CAN_SEE_HIM) String relaNotSeeHim) {
        ResponseEntity<String> responseEntity;
        try {
            friendApplyService.acceptFriendApply(applyId);

            FriendApply friendApply = friendApplyService.getFriendApplyById(applyId);
            // 双方加好友
            friendApplyService.makeFriends(friendApply.getFromUserId(), friendApply.getToUserId(),
                    relaRemark, relaAuth, relaNotSeeMe, relaNotSeeHim);

            User toUser = userService.getUserByUserId(friendApply.getToUserId());
            toUser.setIsFriend(Constant.IS_FRIEND);
            Map<String, String> extras = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            extras.put("serviceType", Constant.PUSH_SERVICE_TYPE_ADD_FRIENDS_ACCEPT);
            extras.put("user", JSON.toJSONString(toUser));
            jpushBiz.sendPushWithoutNotification(friendApply.getFromUserId(), extras);

            responseEntity = new ResponseEntity<>(ResponseMsg.ACCEPT_FRIEND_APPLY_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[acceptFriendApply] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ACCEPT_FRIEND_APPLY_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
