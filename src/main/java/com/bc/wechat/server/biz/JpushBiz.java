package com.bc.wechat.server.biz;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 极光推送处理类
 *
 * @author zhou
 */
@Component("jpushBiz")
public class JpushBiz {
    private static final Logger logger = LoggerFactory.getLogger(JpushBiz.class);

    @Resource
    private JPushClient jPushClient;

    public void sendPush(String alias, String alert, Map<String, String> extras) {
        PlatformNotification android = new PlatformNotification(alert, extras,
                null, null, null) {
            @Override
            protected String getPlatform() {
                return "android";
            }
        };
        PlatformNotification ios = new PlatformNotification(alert, extras,
                null, null, null) {
            @Override
            protected String getPlatform() {
                return "ios";
            }
        };

        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder().
                        addPlatformNotification(android).addPlatformNotification(ios).build())
                .build();

        try {
            PushResult result = jPushClient.sendPush(pushPayload);
            logger.info("Got result - " + result);
        } catch (APIConnectionException e) {
            logger.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
    }
}
