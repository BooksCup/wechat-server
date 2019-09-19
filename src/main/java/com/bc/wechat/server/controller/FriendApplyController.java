package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.FriendApply;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.FriendApplyService;
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
    private FriendApplyService friendApplyService;

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
