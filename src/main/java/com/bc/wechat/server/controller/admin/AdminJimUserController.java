package com.bc.wechat.server.controller.admin;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import com.bc.wechat.server.enums.ResponseMsg;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

}
