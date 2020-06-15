package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Objects;

/**
 * 邮箱
 *
 * @author zhou
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送网页邮件
     *
     * @param to       接收者
     * @param subject  邮件标题
     * @param text     邮件内容
     * @param imageMap 网页中图片map key:cid key value:图片本地路径
     */
    @Override
    public void sendHtmlMessage(String to, String subject, String text, Map<String, String> imageMap) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        for (Map.Entry<String, String> entry : imageMap.entrySet()) {
            Resource image = new ClassPathResource(entry.getValue());
            if (Objects.nonNull(image)) {
                helper.addInline(entry.getKey(), image);
            }
        }
        javaMailSender.send(mimeMessage);
    }
}
