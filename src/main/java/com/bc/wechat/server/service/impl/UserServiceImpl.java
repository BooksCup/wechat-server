package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.mapper.UserMapper;
import com.bc.wechat.server.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUserByLogin(Map<String, String> paramMap) {
        return userMapper.getUserByLogin(paramMap);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void updateUserNickName(Map<String, String> paramMap) {
        userMapper.updateUserNickName(paramMap);
    }

    /**
     * 修改用户微信号
     *
     * @param paramMap 参数map
     */
    @Override
    public void updateUserWxId(Map<String, String> paramMap) {
        userMapper.updateUserWxId(paramMap);
    }
}
