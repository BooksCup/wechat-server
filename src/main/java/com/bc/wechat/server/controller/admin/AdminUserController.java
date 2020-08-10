package com.bc.wechat.server.controller.admin;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.User;
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
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        logger.info("[getUserPageInfo], page: " + page + ", limit: " + limit);
        ResponseEntity<PageInfo<User>> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            PageInfo<User> userPageInfo = userService.getUserPageInfo(page, limit, paramMap);
            responseEntity = new ResponseEntity<>(userPageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
