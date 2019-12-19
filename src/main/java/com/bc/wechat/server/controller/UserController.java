package com.bc.wechat.server.controller;


import java.util.*;

import cn.jmessage.api.JMessageClient;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.UserRelaService;
import com.bc.wechat.server.service.UserService;
import com.bc.wechat.server.utils.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    private JMessageClient jMessageClient;

    @Resource
    private UserService userService;

    @Resource
    private UserRelaService userRelaService;

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
        Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
        paramMap.put("phone", phone);
        paramMap.put("password", password);
        List<User> userList = userService.getUserByLogin(paramMap);
        if (CollectionUtils.isEmpty(userList)) {
            responseEntity = new ResponseEntity<>(new User(),
                    HttpStatus.BAD_REQUEST);
        } else {
            User user = userList.get(0);
            List<User> friendList = userRelaService.getFriendList(user.getUserId());
            user.setFriendList(friendList);

            responseEntity = new ResponseEntity<>(user,
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
    public ResponseEntity<User> register(
            @RequestParam String nickName,
            @RequestParam String phone,
            @RequestParam String password) {
        ResponseEntity<User> responseEntity;
        try {
            boolean isUserExists = userService.checkUserExistsByUserPhone(phone);
            if (isUserExists) {
                logger.info("register error, user exists. phone: " + phone);
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                headers.add("responseCode", ResponseMsg.USER_EXISTS.getResponseCode());
                // responseMessage会乱码，待解决
                headers.add("responseMessage", ResponseMsg.USER_EXISTS.getResponseMessage());
                return new ResponseEntity<>(new User(), headers, HttpStatus.BAD_REQUEST);
            }

            User user = new User(nickName, phone, password);
            String imPassword = CommonUtil.generateRandomNum(6);
            user.setUserImPassword(imPassword);
            userService.addUser(user);

            // 用户注册到极光IM
            jMessageClient.registerAdmins(user.getUserId(), imPassword);
            // 更改用户昵称，头像
            jMessageClient.updateUserInfo(user.getUserId(), user.getUserNickName(),
                    "1970-01-01", user.getUserSign(),
                    0, "", "", user.getUserAvatar());

            responseEntity = new ResponseEntity<>(user,
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("register error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new User(),
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
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userNickName", userNickName);
            userService.updateUserNickName(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_NICK_NAME_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUserNickName error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_NICK_NAME_ERROR.getResponseCode(),
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
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userWxId", userWxId);
            userService.updateUserWxId(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_WX_ID_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUserWxId error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_WX_ID_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改性别
     *
     * @param userId  用户ID
     * @param userSex 用户性别
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改性别", notes = "修改性别")
    @PutMapping(value = "/{userId}/userSex")
    public ResponseEntity<String> updateUserSex(
            @PathVariable String userId,
            @RequestParam String userSex) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userSex", userSex);
            userService.updateUserSex(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_SEX_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUserSex error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_SEX_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改头像
     *
     * @param userId     用户ID
     * @param userAvatar 用户头像
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改头像", notes = "修改头像")
    @PutMapping(value = "/{userId}/userAvatar")
    public ResponseEntity<String> updateUserAvatar(
            @PathVariable String userId,
            @RequestParam String userAvatar) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userAvatar", userAvatar);
            userService.updateUserAvatar(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_AVATAR_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUserAvatar error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_AVATAR_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改签名
     *
     * @param userId   用户ID
     * @param userSign 用户签名
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改签名", notes = "修改签名")
    @PutMapping(value = "/{userId}/userSign")
    public ResponseEntity<String> updateUserSign(
            @PathVariable String userId,
            @RequestParam String userSign) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userSign", userSign);
            userService.updateUserSign(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_SIGN_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateUserSign error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_SIGN_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 搜索用户(用于添加好友)
     *
     * @param userId  查看人用户ID, 用于判断两人是否好友
     * @param keyword 搜索关键字, 手机号/微信号
     * @return ResponseEntity
     */
    @ApiOperation(value = "搜索用户(用于添加好友)", notes = "搜索用户(用于添加好友)")
    @GetMapping(value = "/searchForAddFriends")
    public ResponseEntity<User> searchForAddFriends(
            @RequestParam String userId,
            @RequestParam String keyword) {
        ResponseEntity<User> responseEntity;
        List<User> userList = userService.getUserByKeyword(keyword);
        if (CollectionUtils.isEmpty(userList)) {
            responseEntity = new ResponseEntity<>(new User(),
                    HttpStatus.BAD_REQUEST);
        } else {
            User user = userList.get(0);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("friendId", user.getUserId());
            boolean isFriend = userRelaService.checkIsFriend(paramMap);
            if (isFriend) {
                user.setIsFriend(Constant.IS_FRIEND);
            } else {
                user.setIsFriend(Constant.IS_NOT_FRIEND);
            }

            responseEntity = new ResponseEntity<>(user,
                    HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * 根据ID获取用户
     *
     * @param userId 用户ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> getUserById(
            @PathVariable String userId) {
        ResponseEntity<User> responseEntity;
        try {
            User user = userService.getUserByUserId(userId);
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getUserById error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 生成用户二维码
     *
     * @param userId 用户ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "生成用户二维码", notes = "生成用户二维码")
    @PostMapping(value = "/{userId}/userQrCode")
    public ResponseEntity<String> generateUserQrCode(
            @PathVariable String userId) {
        ResponseEntity<String> responseEntity;
        User user = userService.getUserByUserId(userId);
        boolean result = userService.refreshUserQrCode(user);
        if (result) {
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.REFRESH_USER_QR_CODE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.REFRESH_USER_QR_CODE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
