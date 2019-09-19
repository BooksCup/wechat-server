package com.bc.wechat.server.config;

import cn.jpush.api.JPushClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 极光推送初始化类
 *
 * @author zhou
 */
@Configuration
public class JpushConfig {
    private static final Logger logger = LoggerFactory.getLogger(JpushConfig.class);

    @Value("${jpush.appKey}")
    String appKey;

    @Value("${jpush.masterSecret}")
    String masterSecret;

    @Bean
    public JPushClient client() {
        logger.info("===> appKey: " + appKey + ", masterSecret: " + masterSecret);
        JPushClient jPushClient = null;
        try {
            jPushClient = new JPushClient(masterSecret, appKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jPushClient;
    }
}
