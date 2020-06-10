package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.VerificationCode;

/**
 * 验证码
 *
 * @author zhou
 */
public interface VerificationCodeMapper {
    /**
     * 保存验证码
     *
     * @param verificationCode 验证码
     */
    void addVerificationCode(VerificationCode verificationCode);
}
