package com.bc.wechat.server.service;

import java.util.Map;

public interface UserRelaService {
    /**
     * 检查是否好友关系
     *
     * @param paramMap 参数map
     * @return true:是  false:否
     */
    boolean checkIsFriend(Map<String, String> paramMap);
}
