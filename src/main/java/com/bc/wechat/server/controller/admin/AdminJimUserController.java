package com.bc.wechat.server.controller.admin;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 极光用户
 *
 * @author zhou
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/jimUser")
public class AdminJimUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminJimUserController.class);

    @Autowired
    private JMessageClient jMessageClient;

    @Resource
    private UserService userService;

    /**
     * 检测用户是否在极光中存在
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "检测用户是否在极光中存在", notes = "检测用户是否在极光中存在")
    @GetMapping(value = "/{userId}/exist")
    public ResponseEntity<String> checkJimUserExist(@PathVariable String userId) {
        logger.info("[checkJimUserExist], userId: " + userId);
        ResponseEntity<String> responseEntity;
        try {
            jMessageClient.getUserInfo(userId);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.USER_EXISTS.getResponseCode(), HttpStatus.OK);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.USER_NOT_EXISTS.getResponseCode(), HttpStatus.BAD_REQUEST);
        } catch (APIRequestException e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.USER_NOT_EXISTS.getResponseCode(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.USER_NOT_EXISTS.getResponseCode(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    /**
     * 同步用户至极光
     *
     * @param userId 用户ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "同步用户至极光", notes = "同步用户至极光")
    @PostMapping(value = "")
    public ResponseEntity<String> syncUserToJim(@RequestParam String userId) {
        logger.info("[syncUserToJim], userId: " + userId);
        ResponseEntity<String> responseEntity;
        try {
            User user = userService.getUserByUserId(userId);
            jMessageClient.registerAdmins(user.getUserId(), user.getUserImPassword());
            jMessageClient.updateUserInfo(user.getUserId(), user.getUserNickName(),
                    "1970-01-01", user.getUserSign(),
                    0, "", "", user.getUserAvatar());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SYNC_USER_TO_JIM_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SYNC_USER_TO_JIM_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
