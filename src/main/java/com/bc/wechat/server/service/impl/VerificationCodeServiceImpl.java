package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.VerificationCode;
import com.bc.wechat.server.mapper.VerificationCodeMapper;
import com.bc.wechat.server.service.VerificationCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证码
 *
 * @author zhou
 */
@Service("verificationCodeService")
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Resource
    private VerificationCodeMapper verificationCodeMapper;

    /**
     * 保存验证码
     *
     * @param verificationCode 验证码
     */
    @Override
    public void addVerificationCode(VerificationCode verificationCode) {
        verificationCodeMapper.addVerificationCode(verificationCode);
    }
}
