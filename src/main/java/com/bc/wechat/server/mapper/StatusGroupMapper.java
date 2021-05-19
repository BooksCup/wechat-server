package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.Status;
import com.bc.wechat.server.entity.StatusGroup;

import java.util.List;

/**
 * 状态组
 *
 * @author zhou
 */
public interface StatusGroupMapper {

    /**
     * 获取状态组列表
     *
     * @return 状态组列表
     */
    List<StatusGroup> getStatusGroupList();

    /**
     * 根据状态组ID获取状态列表
     *
     * @param groupId 状态组ID
     * @return 状态列表
     */
    List<Status> getStatusListByGroupId(String groupId);

}