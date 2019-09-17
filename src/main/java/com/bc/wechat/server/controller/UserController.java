package com.bc.wechat.server.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return ResponseEntity
     */
    @ApiOperation(value = "登录", notes = "登录")
    @GetMapping(value = "/login")
    public ResponseEntity<User> login(
            @RequestParam String phone,
            @RequestParam String password) {
        ResponseEntity<User> responseEntity;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phone", phone);
        paramMap.put("password", password);
        List<User> userList = userService.getUserByLogin(paramMap);
        if (CollectionUtils.isEmpty(userList)) {
            responseEntity = new ResponseEntity<>(new User(),
                    HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<>(userList.get(0),
                    HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * 注册
     *
     * @param nickName 昵称
     * @param phone    手机号
     * @param password 密码
     * @return ResponseEntity
     */
    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping(value = "")
    public ResponseEntity<String> register(
            @RequestParam String nickName,
            @RequestParam String phone,
            @RequestParam String password) {
        ResponseEntity<String> responseEntity;
        User user = new User(nickName, phone, password);
        try {
            userService.addUser(user);
            responseEntity = new ResponseEntity<>(ResponseMsg.REGISTER_SUCCESS.value(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("register error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.REGISTER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改昵称
     *
     * @param userId       用户ID
     * @param userNickName 用户昵称
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改昵称", notes = "修改昵称")
    @PutMapping(value = "/{userId}/userNickName")
    public ResponseEntity<String> updateUserNickName(
            @PathVariable String userId,
            @RequestParam String userNickName) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("userNickName", userNickName);
            userService.updateUserNickName(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_NICK_NAME_SUCCESS.value(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUserNickName error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_NICK_NAME_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改微信号
     *
     * @param userId   用户ID
     * @param userWxId 用户微信号
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改微信号", notes = "修改微信号")
    @PutMapping(value = "/{userId}/userWxId")
    public ResponseEntity<String> updateUserWxId(
            @PathVariable String userId,
            @RequestParam String userWxId) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("userWxId", userWxId);
            userService.updateUserWxId(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_WX_ID_SUCCESS.value(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUserWxId error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_WX_ID_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 搜索用户(用于添加好友)
     *
     * @param keyword 搜索关键字, 手机号/微信号
     * @return ResponseEntity
     */
    @ApiOperation(value = "搜索用户(用于添加好友)", notes = "搜索用户(用于添加好友)")
    @GetMapping(value = "/searchForAddFriends")
    public ResponseEntity<User> searchForAddFriends(
            @RequestParam String keyword) {
        ResponseEntity<User> responseEntity;
        List<User> userList = userService.getUserByKeyword(keyword);
        if (CollectionUtils.isEmpty(userList)) {
            responseEntity = new ResponseEntity<>(new User(),
                    HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<>(userList.get(0),
                    HttpStatus.OK);
        }
        return responseEntity;
    }

}
