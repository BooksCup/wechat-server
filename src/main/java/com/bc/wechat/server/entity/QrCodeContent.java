package com.bc.wechat.server.entity;

import java.util.Map;

/**
 * 二维码内容实体
 *
 * @author zhou
 */
public class QrCodeContent {
    public final static String QR_CODE_TYPE_USER = "user";

    private String type;
    private Map<String, Object> contentMap;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<String, Object> contentMap) {
        this.contentMap = contentMap;
    }
}
