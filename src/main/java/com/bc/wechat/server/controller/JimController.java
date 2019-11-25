package com.bc.wechat.server.controller;

import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.user.UserListResult;
import com.bc.wechat.server.cons.Constant;
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
    @PostMapping(value = "/")
    public ResponseEntity<String> addUsersToJim() {
        ResponseEntity<String> responseEntity;
        try {
            List<User> userList = userService.getAllUserList();
            for (User user : userList) {
                jMessageClient.registerAdmins(user.getUserId(), "123456");
                Thread.sleep(1000L);
                jMessageClient.updateUserInfo(user.getUserId(), user.getUserNickName(),
                        "1970-01-01", user.getUserSign(),
                        0, "", "", user.getUserAvatar());

                Thread.sleep(1000L);
            }
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_USER_TO_JIM_SUCCESS.value(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_USER_TO_JIM_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    /**
     * 清除所有JIM用户
     *
     * @param password  高危操作密码
     * @param totalPage 总页数
     * @param pageSize  单页大小
     * @return ResponseEntity
     */
    @ApiOperation(value = "清除JIM的用户", notes = "清除JIM的用户")
    @DeleteMapping(value = "/")
    public ResponseEntity<String> clearJimUsers(@RequestParam String password,
                                                @RequestParam Integer totalPage,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        long beginTimeStamp = System.currentTimeMillis();
        int userCount = 0;
        if (!Constant.HIGH_RISK_OPER_PASSWORD.equals(password)) {
            return new ResponseEntity<>("password not correct!", HttpStatus.BAD_REQUEST);
        }
        // 获取JIM所有用户
        try {
            totalPage = totalPage == null ? 100 : totalPage;

            for (int i = 0; i < totalPage; i++) {
                int count = pageSize;
                UserListResult userListResult = jMessageClient.getAdminListByAppkey(0, count);
                UserInfoResult[] userInfoResults = userListResult.getUsers();
                for (UserInfoResult userInfoResult : userInfoResults) {
                    userCount++;
                    String username = userInfoResult.getUsername();
                    logger.info("username: " + username);

                    // 删除用户
                    jMessageClient.deleteUser(username);
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        long endTimeStamp = System.currentTimeMillis();
        return new ResponseEntity<>("clear finish. clear user count: " + userCount + "cost: "
                + (endTimeStamp - beginTimeStamp) + "ms.", HttpStatus.OK);
    }
}
