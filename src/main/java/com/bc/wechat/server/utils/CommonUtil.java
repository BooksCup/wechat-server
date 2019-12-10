package com.bc.wechat.server.utils;

import cn.jmessage.api.common.model.message.MessageBody;
import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.MsgBody;

import java.text.SimpleDateFormat;
import java.util.*;

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
     *
     * @param msgBody     IM的message bean
     * @param messageType 消息类型
     * @return 极光的message bean
     */
    public static MessageBody generateMessageBody(MsgBody msgBody, String messageType) {
        if (Constant.MSG_TYPE_TEXT.equals(messageType)) {
            // 文字消息
            return new MessageBody.Builder()
                    .setText(msgBody.getText())
                    .addExtras(msgBody.getExtras())
                    .build();
        } else if (Constant.MSG_TYPE_IMAGE.equals(messageType)) {
            // 图片消息
            return new MessageBody.Builder()
                    .setMediaId(msgBody.getMediaId())
                    .setMediaCrc32(msgBody.getMediaCrc32())
                    .setWidth(msgBody.getWidth())
                    .setHeight(msgBody.getHeight())
                    .setFormat(msgBody.getFormat())
                    .setFsize(msgBody.getFsize())
                    .build();
        } else if (Constant.MSG_TYPE_LOCATION.equals(messageType)) {
            // 位置消息
            Map<String, Object> locationMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            locationMap.put("type", Constant.MSG_TYPE_LOCATION);
            locationMap.put("latitude", msgBody.getLatitude());
            locationMap.put("longitude", msgBody.getLongitude());
            locationMap.put("address", msgBody.getAddress());
            locationMap.put("addressDetail", msgBody.getAddressDetail());
            locationMap.put("path", msgBody.getPath());

            return new MessageBody.Builder().setText(JSON.toJSONString(locationMap)).build();
        } else {
            // 默认文字
            return new MessageBody.Builder()
                    .setText(msgBody.getText())
                    .addExtras(msgBody.getExtras())
                    .build();
        }
    }

    public static String subJimString(String jimString, int length) {
        while (jimString.getBytes().length > length - 3) {
            jimString = jimString.substring(0, jimString.length() - 1);
        }
        return jimString + "...";
    }


    /**
     * 生成随机n位数
     *
     * @param len 随机数长度
     * @return 随机n位数
     */
    public static String generateRandomNum(int len) {
        if (len < 1) {
            return "";
        }
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return String.valueOf(rs);
    }
}
