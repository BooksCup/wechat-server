package com.bc.wechat.server.controller;

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
import java.util.List;

/**
 * 极光IM控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("/jim")
public class JimController {

    private static final Logger logger = LoggerFactory.getLogger(JimController.class);

    @Resource
    private UserService userService;

    @Autowired
    private JMessageClient jMessageClient;

    /**
     * 添加用户至极光
     *
     * @return ResponseEntity
     */
    @ApiOperation(value = "添加用户至极光", notes = "添加用户至极光")
    @PostMapping(value = "")
    public ResponseEntity<String> addUsers() {
        ResponseEntity<String> responseEntity;
        try {
            List<User> userList = userService.getAllUserList();
            for (User user : userList) {
                jMessageClient.registerAdmins(user.getUserId(), "123456");
                jMessageClient.updateUserInfo(user.getUserId(), user.getUserNickName(), "1970-01-01", "",
                        0, "", "", user.getUserAvatar());
            }
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_USER_TO_JIM_SUCCESS.value(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_USER_TO_JIM_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return responseEntity;
    }
}
