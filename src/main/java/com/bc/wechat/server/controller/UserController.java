package com.bc.wechat.server.controller;


import java.util.List;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.mapper.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        List<User> users = userMapper.getAll();
        return users;
    }
}
