package com.bc.wechat.server.utils;

import java.util.UUID;

public class CommonUtil {
    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
