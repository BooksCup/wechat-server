package com.bc.wechat.server.utils;

import cn.jmessage.api.common.model.message.MessageBody;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.MsgBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 通用工具类
 *
 * @author zhou
 */
public class CommonUtil {

    /**
     * 生成主键
     *
     * @return 主键
     */
    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 将IM的message bean转为极光的message bean
     * @param msgBody IM的message bean
     * @param messageType 消息类型
     * @return 极光的message bean
     */
    public static MessageBody generateMessageBody(MsgBody msgBody, String messageType) {
        if (Constant.MSG_TYPE_TEXT.equals(messageType)) {
            return new MessageBody.Builder()
                    .setText(msgBody.getText())
                    .addExtras(msgBody.getExtras())
                    .build();
        } else if (Constant.MSG_TYPE_IMAGE.equals(messageType)) {
            return new MessageBody.Builder()
                    .setMediaId(msgBody.getMediaId())
                    .setMediaCrc32(msgBody.getMediaCrc32())
                    .setWidth(msgBody.getWidth())
                    .setHeight(msgBody.getHeight())
                    .setFormat(msgBody.getFormat())
                    .setFsize(msgBody.getFsize())
                    .build();
        } else {
            // 默认文字
            return new MessageBody.Builder()
                    .setText(msgBody.getText())
                    .addExtras(msgBody.getExtras())
                    .build();
        }
    }
}
