package com.bc.wechat.server.config;

import cn.jmessage.api.JMessageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 极光IM初始化类
 * @author zhou
 */
@Configuration
public class ImConfig {
    private static final Logger logger = LoggerFactory.getLogger(ImConfig.class);

    @Value("${jpush.appKey}")
    String appKey;

    @Value("${jpush.masterSecret}")
    String masterSecret;

    @Bean
    public JMessageClient jMessageClient() {
        logger.info("===> appKey: " + appKey + ", masterSecret: " + masterSecret);
        JMessageClient jMessageClient = null;
        try {
            jMessageClient = new JMessageClient(appKey, masterSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jMessageClient;
    }
}
