package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.VerificationCode;

/**
 * 验证码
 *
 * @author zhou
 */
public interface VerificationCodeService {
    /**
     * 保存验证码
     *
     * @param verificationCode 验证码
     */
    void addVerificationCode(VerificationCode verificationCode);
}
