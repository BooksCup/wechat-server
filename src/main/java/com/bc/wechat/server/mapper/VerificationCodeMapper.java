package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.VerificationCode;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户手机号获取有效的验证码列表
     *
     * @param paramMap 参数map
     * @return 获取有效的验证码列表
     */
    List<String> getVerificationCodeList(Map<String, String> paramMap);
}
