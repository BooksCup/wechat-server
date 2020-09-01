package com.bc.wechat.server.controller.admin;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 *
 * @author zhou
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Resource
    private UserService userService;

    /**
     * 查询用户分页信息
     *
     * @param page  当前分页数
     * @param limit 分页大小
     * @return 用户分页信息
     */
    @ApiOperation(value = "查询用户分页信息", notes = "查询用户分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<User>> getUserPageInfo(
            @RequestParam(required = false) String userNickName,
            @RequestParam(required = false) String userPhone,
            @RequestParam(required = false) String userWxId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        logger.info("[getUserPageInfo], page: " + page + ", limit: " + limit);
        ResponseEntity<PageInfo<User>> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userNickName", userNickName);
            paramMap.put("userPhone", userPhone);
            paramMap.put("userWxId", userWxId);
            PageInfo<User> userPageInfo = userService.getUserPageInfo(page, limit, paramMap);
            responseEntity = new ResponseEntity<>(userPageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 初始化特殊用户
     *
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "初始化特殊用户", notes = "初始化特殊用户")
    @PostMapping(value = "/init")
    public ResponseEntity<String> initSpecialUser() {
        logger.info("initSpecialUser");
        ResponseEntity<String> responseEntity;
        try {
            initWeixin();
            initFileHelper();
            responseEntity = new ResponseEntity<>(ResponseMsg.INIT_SPECIAL_USER_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.INIT_SPECIAL_USER_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 初始化"微信团队"
     */
    private void initWeixin() {
        User user = new User();
        user.setUserId(Constant.SPECIAL_USER_ID_WEIXIN);
        user.setUserWxId("weixin");
        user.setUserType(Constant.USER_TYPE_WEIXIN);
        user.setUserNickName("微信团队");
        user.setUserPhone("00000000000");
        user.setUserImPassword("000000");
        user.setUserAvatar("https://erp-wd-com.oss-cn-hangzhou.aliyuncs.com/user_avatar_weixin.jpg");
        if (userService.checkUserExistsByUserPhone(user.getUserPhone())) {
            userService.deleteUserById(user.getUserId());
            userService.addUser(user);
        } else {
            userService.addUser(user);
        }
    }

    /**
     * 初始化"文件传输助手"
     */
    private void initFileHelper() {
        User user = new User();
        user.setUserId(Constant.SPECIAL_USER_ID_FILEHELPER);
        user.setUserWxId("filehelper");
        user.setUserType(Constant.USER_TYPE_WEIXIN);
        user.setUserNickName("文件传输助手");
        user.setUserPhone("00000000001");
        user.setUserImPassword("000000");
        user.setUserAvatar("https://erp-wd-com.oss-cn-hangzhou.aliyuncs.com/user_avatar_filehelper.jpg");
        if (userService.checkUserExistsByUserPhone(user.getUserPhone())) {
            userService.deleteUserById(user.getUserId());
            userService.addUser(user);
        } else {
            userService.addUser(user);
        }
    }
}
