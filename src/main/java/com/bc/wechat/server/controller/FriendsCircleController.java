package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.FriendsCircle;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.FriendsCircleService;
import com.bc.wechat.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private UserService userService;

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

            // 更新该用户最新n张朋友圈照片
            List<String> lastestCirclePhotoList = friendsCircleService.getLastestCirclePhotosByUserId(userId);
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("userLastestCirclePhotos", JSON.toJSONString(lastestCirclePhotoList));
            userService.updateUserLastestCirclePhotos(paramMap);


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
     * @param userId    用户ID
     * @param pageSize  每页数量
     * @param timestamp 时间戳
     * @return 朋友圈列表
     */
    @ApiOperation(value = "查找朋友圈列表", notes = "查找朋友圈列表")
    @GetMapping(value = "")
    public ResponseEntity<List<FriendsCircle>> getFriendsCircleListByUserId(
            @RequestParam String userId,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Long timestamp) {
        ResponseEntity<List<FriendsCircle>> responseEntity;
        try {
            if (0L == timestamp || null == timestamp) {
                timestamp = System.currentTimeMillis();
            }

            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("pageSize", pageSize);
            paramMap.put("timestamp", timestamp);
            List<FriendsCircle> friendsCircleList = friendsCircleService.getFriendsCircleListByUserId(paramMap);
            for (FriendsCircle friendsCircle : friendsCircleList) {
                List<User> likeUserList = friendsCircleService.getLikeUserListByCircleId(friendsCircle.getCircleId());
                friendsCircle.setLikeUserList(likeUserList);
            }

            responseEntity = new ResponseEntity<>(friendsCircleList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
