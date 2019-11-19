package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.FriendsCircleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 朋友圈控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/friendsCircle")
public class FriendsCircleController {

    @Resource
    private FriendsCircleService friendsCircleService;

    /**
     * 发朋友圈
     *
     * @param userId  用户ID
     * @param content 朋友圈内容
     * @param photos  朋友圈图片
     * @return ResponseEntity
     */
    @ApiOperation(value = "发朋友圈", notes = "发朋友圈")
    @PostMapping(value = "")
    public ResponseEntity<String> addFriendsCircle(
            @RequestParam String userId,
            @RequestParam String content,
            @RequestParam String photos) {
        ResponseEntity<String> responseEntity;
        try {
            FriendsCircle friendsCircle = new FriendsCircle(userId, content, photos);
            friendsCircleService.addFriendsCircle(friendsCircle);
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIENDS_CIRCLE_SUCCESS.value(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_FRIENDS_CIRCLE_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * 查找某个用户的朋友圈列表
     *
     * @param userId 用户ID
     * @return 朋友圈列表
     */
    @ApiOperation(value = "查找朋友圈列表", notes = "查找朋友圈列表")
    @GetMapping(value = "")
    public ResponseEntity<List<FriendsCircle>> getFriendsCircleListByUserId(
            @RequestParam String userId) {
        ResponseEntity<List<FriendsCircle>> responseEntity;
        try {
            List<FriendsCircle> friendsCircleList = friendsCircleService.getFriendsCircleListByUserId(userId);
            responseEntity = new ResponseEntity<>(friendsCircleList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
