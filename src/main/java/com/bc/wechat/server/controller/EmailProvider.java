package com.bc.wechat.server.controller;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.EmailService;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件
 *
 * @author zhou
 */
@RestController
@RequestMapping("/email")
public class EmailProvider {

    private static final Logger logger = LoggerFactory.getLogger(EmailProvider.class);

    @Resource
    private EmailService emailService;

    /**
     * 邮箱验证
     *
     * @param to       接收者
     * @param subject  邮件标题
     * @param wechatId 微信号
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "邮箱验证", notes = "邮箱验证")
    @PostMapping(value = "/link")
    public ResponseEntity<String> linkEmail(@RequestParam String to,
                                            @RequestParam String subject,
                                            @RequestParam String wechatId) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> imageMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            imageMap.put("bg-letter", "email-template/img/bg-letter.png");
            imageMap.put("bg-wechat", "email-template/img/bg-wechat.jpg");
            imageMap.put("icon-wechat", "email-template/img/icon-wechat.jpg");

            emailService.sendHtmlMessage(to, subject, buildLinkEmailContent(to, wechatId), imageMap);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SEND_MAIL_MESSAGE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SEND_MAIL_MESSAGE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 创建网页邮件内容
     *
     * @param email    邮箱
     * @param wechatId 微信号
     * @return 网页邮件内容
     * @throws IOException 异常
     */
    private String buildLinkEmailContent(String email, String wechatId) throws IOException {
        // 加载邮件html模板
        String fileName = "email-template/link-email-template.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line;
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            logger.error("[buildLinkEmailContent] error，fileName:{}", fileName, e);
        } finally {
            inputStream.close();
            fileReader.close();
        }
        // 填充html模板
        String htmlText = MessageFormat.format(buffer.toString(), wechatId, email, wechatId);
        return htmlText;
    }
}
