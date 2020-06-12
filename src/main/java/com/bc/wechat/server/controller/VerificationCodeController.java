package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.VerificationCode;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.VerificationCodeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 验证码
 *
 * @author zhou
 */
@RestController
@RequestMapping("/verificationCode")
public class VerificationCodeController {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeController.class);

    @Resource
    private VerificationCodeService verificationCodeService;

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @PostMapping(value = "")
    public ResponseEntity<String> getVerificationCode(
            @RequestParam String phone,
            @RequestParam String serviceType) {
        ResponseEntity<String> responseEntity;
        try {
            VerificationCode verificationCode = new VerificationCode(phone, "123456", serviceType, 10 * 60);
            verificationCodeService.addVerificationCode(verificationCode);
            responseEntity = new ResponseEntity<>(ResponseMsg.GET_VERIFICATION_CODE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[getVerificationCode] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.GET_VERIFICATION_CODE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
