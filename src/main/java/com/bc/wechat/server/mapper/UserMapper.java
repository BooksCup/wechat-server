package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getUserByLogin(Map<String, String> paramMap);

    void addUser(User user);
}
