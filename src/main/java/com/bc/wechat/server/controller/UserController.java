package com.bc.wechat.server.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @ApiOperation(value = "登录", notes = "登录")
    @GetMapping(value = "/login")
    public ResponseEntity<String> login(
            @RequestParam String phone,
            @RequestParam String password) {
        ResponseEntity<String> responseEntity;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phone", phone);
        paramMap.put("password", password);
        List<User> userList = userMapper.getUserByLogin(paramMap);
        if (CollectionUtils.isEmpty(userList)) {
            responseEntity = new ResponseEntity<>(ResponseMsg.LOGIN_ERROR.value(),
                    HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<>(ResponseMsg.LOGIN_SUCCESS.value(),
                    HttpStatus.OK);
        }
        return responseEntity;
    }
}
