package com.bc.wechat.server.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.mapper.UserMapper;
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
    private UserMapper userMapper;

    @ApiOperation(value = "登录", notes = "登录")
    @GetMapping(value = "/login")
    public ResponseEntity<User> login(
            @RequestParam String phone,
            @RequestParam String password) {
        ResponseEntity<User> responseEntity;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phone", phone);
        paramMap.put("password", password);
        List<User> userList = userMapper.getUserByLogin(paramMap);
        if (CollectionUtils.isEmpty(userList)) {
            responseEntity = new ResponseEntity<>(new User(),
                    HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<>(userList.get(0),
                    HttpStatus.OK);
        }
        return responseEntity;
    }

    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping(value = "")
    public ResponseEntity<String> register(
            @RequestParam String nickName,
            @RequestParam String phone,
            @RequestParam String password) {
        ResponseEntity<String> responseEntity;
        User user = new User(nickName, phone, password);
        try {
            userMapper.addUser(user);
            responseEntity = new ResponseEntity<>(ResponseMsg.REGISTER_SUCCESS.value(),
                    HttpStatus.OK);
        } catch (Exception e) {
            logger.error("register error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.REGISTER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
