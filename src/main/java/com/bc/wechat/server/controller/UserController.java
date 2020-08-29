package com.bc.wechat.server.controller;


import java.util.*;

import cn.jmessage.api.JMessageClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.*;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.*;
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

    @Resource
    private MomentsService momentsService;

    @Resource
    private AddressService addressService;

    @Resource
    private SysLogService sysLogService;

    @Resource
    private UserLoginDeviceService userLoginDeviceService;

    @Resource
    private FriendApplyService friendApplyService;

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
            @RequestParam(required = false, defaultValue = Constant.LOGIN_TYPE_PHONE_AND_PASSWORD) String loginType,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String verificationCode,
            @RequestParam(required = false) String otherAccount,
            @RequestParam(required = false) String deviceInfo) {
        ResponseEntity<User> responseEntity;
        logger.info("[login] phone: " + phone);
        List<User> userList = userService.getUserByLogin(loginType, phone, password, verificationCode, otherAccount);

        StringBuffer sysLogBuffer = new StringBuffer();

        if (CollectionUtils.isEmpty(userList)) {
            responseEntity = new ResponseEntity<>(new User(),
                    HttpStatus.BAD_REQUEST);

            sysLogBuffer.append("phone: ").append(phone).append(", ")
                    .append("status: login error.");
            sysLogService.addSysLog(new SysLog(Constant.SYS_LOG_TYPE_LOG_IN, sysLogBuffer.toString()));
        } else {
            User user = userList.get(0);
            List<User> friendList = userRelaService.getFriendList(user.getUserId());
            user.setFriendList(friendList);

            responseEntity = new ResponseEntity<>(user,
                    HttpStatus.OK);

            // 修改最后一次登录时间
            userService.updateUserLastLoginTime(user.getUserId());

            // 保存系统日志
            sysLogBuffer.append("phone: ").append(phone).append(", ")
                    .append("status: login success.");
            sysLogService.addSysLog(new SysLog(Constant.SYS_LOG_TYPE_LOG_IN, user.getUserId(), sysLogBuffer.toString()));

            // 保存登录设备信息
            try {
                UserLoginDevice userLoginDevice = JSONObject.parseObject(deviceInfo, UserLoginDevice.class);
                userLoginDevice.setDeviceId(CommonUtil.generateId());
                userLoginDevice.setUserId(user.getUserId());
                userLoginDeviceService.addUserLoginDevice(userLoginDevice);
            } catch (Exception e) {
                logger.error("[login] parse DeviceInfo error: " + e.getMessage());
            }
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
            @RequestParam String password,
            @RequestParam(required = false) String avatar) {
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
            // 存在可能重复的BUG
            String initWxId = CommonUtil.generateInitWxId();
            user.setUserWxId(initWxId);
            user.setUserAvatar(avatar);
            user.setUserType(Constant.USER_TYPE_REG);
            userService.addUser(user);

            // 用户注册到极光IM
            jMessageClient.registerAdmins(user.getUserId(), imPassword);
            // 更改用户昵称，头像
            jMessageClient.updateUserInfo(user.getUserId(), user.getUserNickName(),
                    "1970-01-01", user.getUserSign(),
                    0, "", "", user.getUserAvatar());

            // 添加几个默认好友
            friendApplyService.makeFriends(user.getUserId(), Constant.SPECIAL_USER_ID_WEIXIN,
                    "", "", "", "");

            List<User> friendList = userRelaService.getFriendList(user.getUserId());
            user.setFriendList(friendList);

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
            paramMap.put("userWxIdModifyFlag", Constant.USER_WX_ID_MODIFY_FLAG_TRUE);
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
            logger.error("[updateUserSex] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_SEX_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改地区
     *
     * @param userId     用户ID
     * @param userRegion 用户地区
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改地区", notes = "修改地区")
    @PutMapping(value = "/{userId}/userRegion")
    public ResponseEntity<String> updateUserRegion(
            @PathVariable String userId,
            @RequestParam String userRegion) {
        ResponseEntity<String> responseEntity;
        try {
            logger.info("[updateUserRegion] userId: " + userId + ", userRegion: " + userRegion);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userRegion", userRegion);
            userService.updateUserRegion(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_REGION_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[updateUserRegion] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_REGION_ERROR.getResponseCode(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PutMapping(value = "/{userId}/userPassword")
    public ResponseEntity<String> updateUserPassword(
            @PathVariable String userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        ResponseEntity<String> responseEntity;
        try {
            User user = userService.getUserByUserId(userId);
            if (!oldPassword.equals(user.getUserPassword())) {
                return new ResponseEntity<>(ResponseMsg.OLD_PASSWORD_INCORRECT.getResponseCode(),
                        HttpStatus.BAD_REQUEST);
            }
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userPassword", newPassword);
            userService.updateUserPassword(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_PASSWORD_SUCCESS.getResponseCode(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[updateUserPassword] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_PASSWORD_ERROR.getResponseCode(),
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
            logger.error("[updateUserAvatar] error: " + e.getMessage());
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
            paramMap.put("status", Constant.RELA_STATUS_FRIEND);
            boolean isFriend = userRelaService.checkIsFriend(paramMap);
            if (isFriend) {
                user.setIsFriend(Constant.IS_FRIEND);
            } else {
                user.setIsFriend(Constant.IS_NOT_FRIEND);
            }

            // 来源
            if (keyword.equals(user.getUserPhone())) {
                user.setFriendSource(Constant.FRIENDS_SOURCE_BY_PHONE);
            } else if (keyword.equals(user.getUserWxId())) {
                user.setFriendSource(Constant.FRIENDS_SOURCE_BY_WX_ID);
            }

            paramMap.clear();
            paramMap.put("userId", userId);
            paramMap.put("friendId", user.getUserId());
            List<UserRela> userRelaList = userRelaService.getUserRelaListByUserIdAndFriendId(paramMap);
            if (!CollectionUtils.isEmpty(userRelaList)) {
                UserRela userRela = userRelaList.get(0);
                user.setUserFriendRemark(userRela.getRelaFriendRemark());
                user.setUserFriendPhone(userRela.getRelaFriendPhone());
                user.setUserFriendDesc(userRela.getRelaFriendDesc());
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

    /**
     * 更新用户最新N张朋友圈图片
     *
     * @param userId 用户ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "更新用户最新N张朋友圈图片", notes = "更新用户最新N张朋友圈图片")
    @PutMapping(value = "/{userId}/moments")
    public ResponseEntity<String> refreshUserLastestMomentsPhotos(
            @PathVariable String userId) {
        ResponseEntity<String> responseEntity;
        try {
            // 更新该用户最新n张朋友圈照片
            List<String> lastestMomentsPhotoList = momentsService.getLastestMomentsPhotosByUserId(userId);

            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userLastestMomentsPhotos", JSON.toJSONString(lastestMomentsPhotoList));
            userService.updateUserLastestMomentsPhotos(paramMap);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.REFRESH_USER_LASTEST_MOMENTS_PHOTOS_SUCCESS.getResponseCode(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("refreshUserLastestMomentsPhotos error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.REFRESH_USER_LASTEST_MOMENTS_PHOTOS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查找某个用户发布的朋友圈列表
     *
     * @param userId    用户ID
     * @param pageSize  每页数量
     * @param timestamp 时间戳
     * @return 朋友圈列表
     */
    @ApiOperation(value = "获取用户发布的朋友圈列表", notes = "获取用户发布的朋友圈列表")
    @GetMapping(value = "/{userId}/moments")
    public ResponseEntity<List<Moments>> getMomentsListByPublishUserId(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Long timestamp) {
        ResponseEntity<List<Moments>> responseEntity;
        try {

            if (0L == timestamp || null == timestamp) {
                timestamp = System.currentTimeMillis();
            }

            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("pageSize", pageSize);
            paramMap.put("timestamp", timestamp);
            List<Moments> momentsList = momentsService.getMomentsListByPublishUserId(paramMap);
            responseEntity = new ResponseEntity<>(momentsList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getMomentsListByPublishUserId] error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改用户朋友圈封面
     *
     * @param userId       用户ID
     * @param momentsCover 朋友圈封面
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改用户朋友圈封面", notes = "修改用户朋友圈封面")
    @PutMapping(value = "/{userId}/momentsCover")
    public ResponseEntity<String> updateUserMomentsCover(
            @PathVariable String userId,
            @RequestParam String momentsCover) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("momentsCover", momentsCover);
            userService.updateUserMomentsCover(paramMap);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.UPDATE_USER_MOMENTS_COVER_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[updateUserMomentsCover] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.UPDATE_USER_MOMENTS_COVER_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * 删除好友
     *
     * @param userId   用户ID
     * @param friendId 好友ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "删除好友", notes = "删除好友")
    @DeleteMapping(value = "/{userId}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(
            @PathVariable String userId,
            @PathVariable String friendId) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("friendId", friendId);
            userRelaService.deleteFriend(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_FRIEND_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[deleteFriend] error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_FRIEND_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 根据ID获取朋友详情
     *
     * @param userId   用户ID
     * @param friendId 朋友ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "根据ID获取朋友详情", notes = "根据ID获取朋友详情")
    @GetMapping(value = "/{userId}/friends/{friendId}")
    public ResponseEntity<User> getFriendById(
            @PathVariable String userId,
            @PathVariable String friendId) {
        ResponseEntity<User> responseEntity;
        try {
            User user = userService.getUserByUserId(friendId);

            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("friendId", user.getUserId());
            paramMap.put("status", Constant.RELA_STATUS_FRIEND);

            boolean isFriend = userRelaService.checkIsFriend(paramMap);
            if (isFriend) {
                user.setIsFriend(Constant.IS_FRIEND);
            } else {
                user.setIsFriend(Constant.IS_NOT_FRIEND);
            }

            paramMap.clear();
            paramMap.put("userId", userId);
            paramMap.put("friendId", user.getUserId());
            List<UserRela> userRelaList = userRelaService.getUserRelaListByUserIdAndFriendId(paramMap);

            if (!CollectionUtils.isEmpty(userRelaList)) {
                UserRela userRela = userRelaList.get(0);
                user.setUserFriendPhone(userRela.getRelaFriendPhone());
                user.setUserFriendRemark(userRela.getRelaFriendRemark());
                user.setUserFriendDesc(userRela.getRelaFriendDesc());
                user.setIsStarFriend(userRela.getRelaIsStarFriend());
            }

            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getFriendById] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改用户备注信息
     *
     * @param userId       用户ID
     * @param friendId     好友ID
     * @param friendRemark 用户备注
     * @param friendPhone  用户手机号
     * @param friendDesc   用户描述
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改用户备注信息", notes = "修改用户备注信息")
    @PutMapping(value = "/{userId}/remarks")
    public ResponseEntity<String> updateUserRemarks(
            @PathVariable String userId,
            @RequestParam(required = false) String friendId,
            @RequestParam(required = false) String friendRemark,
            @RequestParam(required = false) String friendPhone,
            @RequestParam(required = false) String friendDesc) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("friendId", friendId);

            List<UserRela> userRelaList = userRelaService.getUserRelaListByUserIdAndFriendId(paramMap);

            UserRela userRela = new UserRela(userId, friendId, friendRemark, friendPhone, friendDesc);

            if (CollectionUtils.isEmpty(userRelaList)) {
                // 用户关系不存在
                // 非好友
                // insert
                userRela.setRelaStatus(Constant.RELA_STATUS_STRANGER);
                userRelaService.addUserRela(userRela);
            } else {
                // 用户关系存在
                // update
                userRela.setRelaId(userRelaList.get(0).getRelaId());
                userRelaService.updateUserRela(userRela);
            }

            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_REMARKS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[updateUserRemarks] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_REMARKS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 设置或取消星标朋友
     *
     * @param userId       用户ID
     * @param friendId     好友ID
     * @param isStarFriend 是否星标好友 "0":否 "1":"是"
     * @return ResponseEntity
     */
    @ApiOperation(value = "设置或取消星标朋友", notes = "设置或取消星标朋友")
    @PutMapping(value = "/{userId}/starFriend")
    public ResponseEntity<String> updateUserStarFriend(
            @PathVariable String userId,
            @RequestParam String friendId,
            @RequestParam String isStarFriend) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("friendId", friendId);
            paramMap.put("isStarFriend", isStarFriend);

            userRelaService.updateUserStarFriend(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_STAR_FRIEND_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[updateUserStarFriend] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_USER_STAR_FRIEND_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 获取用户的地址列表
     *
     * @param userId 用户ID
     * @return 地址列表
     */
    @ApiOperation(value = "获取用户的地址列表", notes = "获取用户的地址列表")
    @GetMapping(value = "/{userId}/address")
    public ResponseEntity<List<Address>> getAddressListByUserId(
            @PathVariable String userId) {
        ResponseEntity<List<Address>> responseEntity;
        try {
            List<Address> addressList = addressService.getAddressListByUserId(userId);
            responseEntity = new ResponseEntity<>(addressList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getAddressListByUserId] error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 保存地址
     *
     * @param userId          用户ID
     * @param addressName     收货人
     * @param addressPhone    手机号码
     * @param addressProvince 地区信息-省
     * @param addressCity     地区信息-市
     * @param addressDistrict 地区信息-区
     * @param addressDetail   详细地址
     * @param addressPostCode 邮政编码
     * @return
     */
    @ApiOperation(value = "保存地址", notes = "保存地址")
    @PostMapping(value = "/{userId}/address")
    public ResponseEntity<String> addAddress(
            @PathVariable String userId,
            @RequestParam String addressName,
            @RequestParam String addressPhone,
            @RequestParam String addressProvince,
            @RequestParam String addressCity,
            @RequestParam String addressDistrict,
            @RequestParam String addressDetail,
            @RequestParam(required = false) String addressPostCode) {
        ResponseEntity<String> responseEntity;
        try {
            Address address = new Address(userId, addressName, addressPhone, addressProvince,
                    addressCity, addressDistrict, addressDetail, addressPostCode);
            logger.info("[addAddress] address: " + address);
            addressService.addAddress(address);
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_USER_ADDRESS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[addAddress] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_USER_ADDRESS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改地址
     *
     * @param userId          用户ID
     * @param addressId       地址ID
     * @param addressName     收货人
     * @param addressPhone    手机号码
     * @param addressProvince 地区信息-省
     * @param addressCity     地区信息-市
     * @param addressDistrict 地区信息-区
     * @param addressDetail   详细地址
     * @param addressPostCode 邮政编码
     * @return ResponseEntity
     */
    @ApiOperation(value = "修改地址", notes = "修改地址")
    @PutMapping(value = "/{userId}/address/{addressId}")
    public ResponseEntity<String> modifyAddress(
            @PathVariable String userId,
            @PathVariable String addressId,
            @RequestParam String addressName,
            @RequestParam String addressPhone,
            @RequestParam String addressProvince,
            @RequestParam String addressCity,
            @RequestParam String addressDistrict,
            @RequestParam String addressDetail,
            @RequestParam(required = false) String addressPostCode) {
        ResponseEntity<String> responseEntity;
        try {
            Address address = new Address(userId, addressId, addressName, addressPhone, addressProvince,
                    addressCity, addressDistrict, addressDetail, addressPostCode);
            logger.info("[modifyAddress] address: " + address);
            addressService.modifyAddress(address);
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_USER_ADDRESS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[modifyAddress] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_USER_ADDRESS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改地址
     *
     * @param addressId 地址ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "删除地址", notes = "删除地址")
    @DeleteMapping(value = "/{userId}/address/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable String addressId) {
        ResponseEntity<String> responseEntity;
        try {
            logger.info("[deleteAddress] addressId: " + addressId);
            addressService.deleteAddress(addressId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_USER_ADDRESS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[deleteAddress] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_USER_ADDRESS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 获取用户的登录设备信息列表
     *
     * @param userId 用户ID
     * @return 登录设备信息列表
     */
    @ApiOperation(value = "获取用户的登录设备信息列表", notes = "获取用户的登录设备信息列表")
    @GetMapping(value = "/{userId}/devices")
    public ResponseEntity<List<UserLoginDevice>> getDeviceInfoListByUserId(
            @PathVariable String userId) {
        ResponseEntity<List<UserLoginDevice>> responseEntity;
        try {
            List<UserLoginDevice> userLoginDeviceList = userLoginDeviceService.getUserLoginDeviceListByUserId(userId);
            responseEntity = new ResponseEntity<>(userLoginDeviceList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getDeviceInfoListByUserId] error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @ApiOperation(value = "修改登录设备信息", notes = "修改登录设备信息")
    @PutMapping(value = "/{userId}/devices/{deviceId}")
    public ResponseEntity<String> modifyDevice(
            @PathVariable String deviceId,
            @RequestParam String phoneModelAlias) {
        ResponseEntity<String> responseEntity;
        try {
            logger.info("[modifyDevice] deviceId: " + deviceId + ", phoneModelAlias: " + phoneModelAlias);
            UserLoginDevice userLoginDevice = new UserLoginDevice();
            userLoginDevice.setDeviceId(deviceId);
            userLoginDevice.setPhoneModelAlias(phoneModelAlias);
            userLoginDeviceService.updateUserLoginDevice(userLoginDevice);

            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_DEVICE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[modifyDevice] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_DEVICE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 绑定邮箱
     * ps:只是做个流程，不然需要做链接加密，时间戳认证etc
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "绑定邮箱", notes = "绑定邮箱")
    @GetMapping(value = "/{userId}/emailLink")
    public ResponseEntity<String> linkEmail(
            @PathVariable String userId,
            @RequestParam String email) {
        ResponseEntity<String> responseEntity;
        try {
            logger.info("[linkEmail] userId: " + userId);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userEmail", email);
            // 邮箱已绑定但为验证
            paramMap.put("userIsEmailLinked", Constant.EMAIL_VERIFIED);
            userService.updateUserEmail(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.LINK_EMAIL_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[linkEmail] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.LINK_EMAIL_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 解绑邮箱
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "解绑邮箱", notes = "解绑邮箱")
    @DeleteMapping(value = "/{userId}/emailLink")
    public ResponseEntity<String> unLinkEmail(
            @PathVariable String userId) {
        ResponseEntity<String> responseEntity;
        try {
            logger.info("[unLinkEmail] userId: " + userId);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userEmail", "");
            // 邮箱已绑定但为验证
            paramMap.put("userIsEmailLinked", Constant.EMAIL_NOT_LINK);
            userService.updateUserEmail(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.UNLINK_EMAIL_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[unLinkEmail] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UNLINK_EMAIL_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 绑定QQ号
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "绑定QQ号", notes = "绑定QQ号")
    @PostMapping(value = "/{userId}/qqIdLink")
    public ResponseEntity<String> linkQqId(
            @PathVariable String userId,
            @RequestParam String userQqId,
            @RequestParam String userQqPassword) {
        ResponseEntity<String> responseEntity;
        try {
            logger.info("[linkQqId] userId: " + userId + ", userQqId: " + userQqId);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userQqId", userQqId);
            paramMap.put("userQqPassword", userQqPassword);
            // 邮箱已绑定但为验证
            paramMap.put("userIsQqLinked", Constant.QQ_ID_LINKED);
            userService.updateUserQqId(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.LINK_QQ_ID_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[linkQqId] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.LINK_QQ_ID_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 解绑QQ号
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "解绑QQ号", notes = "解绑QQ号")
    @DeleteMapping(value = "/{userId}/qqIdLink")
    public ResponseEntity<String> unLinkQqId(
            @PathVariable String userId) {
        ResponseEntity<String> responseEntity;
        try {
            logger.info("[unLinkQqId] userId: " + userId);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userQqId", "");
            paramMap.put("userQqPassword", "");
            // 邮箱已绑定但为验证
            paramMap.put("userIsQqLinked", Constant.QQ_ID_NOT_LINK);
            userService.updateUserQqId(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.UNLINK_QQ_ID_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[unLinkQqId] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UNLINK_QQ_ID_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 获取通讯录里的微信好友列表
     *
     * @param phones 手机号列表(json格式)
     * @return 通讯录里的微信好友列表
     */
    @ApiOperation(value = "获取通讯录里的微信好友列表", notes = "获取通讯录里的微信好友列表")
    @PostMapping(value = "/{userId}/phoneContact")
    public ResponseEntity<List<User>> getPhoneContactList(
            @RequestParam String phones) {
        ResponseEntity<List<User>> responseEntity;
        try {
            List<String> phoneList = new ArrayList<>();
            try {
                phoneList = JSON.parseArray(phones, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("[getPhoneContactList], phoneList: " + phoneList);
            List<User> userList = userService.getUserListByPhoneList(phoneList);
            responseEntity = new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getPhoneContactList] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 保存搜索历史
     *
     * @param userId  用户ID
     * @param keyword 搜索关键字
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "保存搜索历史", notes = "保存搜索历史")
    @PostMapping(value = "/{userId}/searchHistory")
    public ResponseEntity<String> saveSearchHistory(
            @PathVariable String userId,
            @RequestParam String keyword) {
        logger.info("[saveSearchHistory], userId: " + userId + ", keyword: " + keyword);
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("id", CommonUtil.generateId());
            paramMap.put("userId", userId);
            paramMap.put("keyword", keyword);
            userService.saveSearchHistory(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.SAVE_SEARCH_HISTORY_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[saveSearchHistory] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.SAVE_SEARCH_HISTORY_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
