package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.StatusGroup;

import java.util.List;

/**
 * 状态组
 *
 * @author zhou
 */
public interface StatusGroupService {

    /**
     * 获取状态组列表
     *
     * @return 状态组列表
     */
    List<StatusGroup> getStatusGroupList();

}