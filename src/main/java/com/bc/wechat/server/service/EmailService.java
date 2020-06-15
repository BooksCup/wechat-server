package com.bc.wechat.server.service;

import java.util.Map;

/**
 * 邮箱
 *
 * @author zhou
 */
public interface EmailService {
    /**
     * 发送网页邮件
     *
     * @param to       接收者
     * @param subject  邮件标题
     * @param text     邮件内容
     * @param imageMap 网页中图片map key:cid key value:图片本地路径
     */
    void sendHtmlMessage(String to, String subject, String text, Map<String, String> imageMap) throws Exception;
}
