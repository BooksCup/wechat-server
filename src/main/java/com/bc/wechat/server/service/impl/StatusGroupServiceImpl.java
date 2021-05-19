package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.Status;
import com.bc.wechat.server.entity.StatusGroup;
import com.bc.wechat.server.mapper.StatusGroupMapper;
import com.bc.wechat.server.service.StatusGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 状态组
 *
 * @author zhou
 */
@Service("statusGroupService")
public class StatusGroupServiceImpl implements StatusGroupService {

    @Resource
    StatusGroupMapper statusGroupMapper;

    /**
     * 获取状态组列表
     *
     * @return 状态组列表
     */
    @Override
    public List<StatusGroup> getStatusGroupList() {
        List<StatusGroup> statusGroupList = statusGroupMapper.getStatusGroupList();
        if (!CollectionUtils.isEmpty(statusGroupList)) {
            for (StatusGroup statusGroup : statusGroupList) {
                List<Status> statusList = statusGroupMapper.getStatusListByGroupId(statusGroup.getGroupId());
                statusGroup.setStatusList(statusList);
            }
        }
        return statusGroupList;
    }

}