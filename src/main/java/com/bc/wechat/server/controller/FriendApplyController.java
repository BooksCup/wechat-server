package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.biz.JpushBiz;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.FriendApply;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.FriendApplyService;
import com.bc.wechat.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private JpushBiz jpushBiz;

    @ApiOperation(value = "新增好友申请", notes = "新增好友申请")
    @PostMapping(value = "")
    public ResponseEntity<String> addFriendApply(
            @RequestParam String fromUserId,
            @RequestParam String toUserId,
            @RequestParam String applyRemark) {
        ResponseEntity<String> responseEntity;
        FriendApply friendApply = new FriendApply(fromUserId, toUserId, applyRemark);
        try {
            friendApplyService.addFriendApply(friendApply);

            User user = userService.getUserByUserId(fromUserId);
            String alert = user.getUserNickName() + "请求加你为好友";

            Map<String, String> extras = new HashMap<>();
            extras.put("serviceType", Constant.PUSH_SERVICE_TYPE_ADD_FRIENDS_APPLY);

            friendApply.setFromUserNickName(user.getUserNickName());
            extras.put("friendApply", JSON.toJSONString(friendApply));

            jpushBiz.sendPush(toUserId, alert, extras);

            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIEND_APPLY_SUCCESS.value(),
                    HttpStatus.OK);

        } catch (Exception e) {
            logger.error("addFriendApply error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIEND_APPLY_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
