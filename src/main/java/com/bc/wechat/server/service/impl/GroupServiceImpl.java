package com.bc.wechat.server.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.group.CreateGroupResult;
import cn.jmessage.api.group.GroupInfoResult;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Group;
import com.bc.wechat.server.entity.GroupMembers;
import com.bc.wechat.server.mapper.GroupMapper;
import com.bc.wechat.server.service.GroupService;
import com.bc.wechat.server.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群组业务类实现类
 *
 * @author zhou
 */
@Service("groupService")
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private JMessageClient jMessageClient;

    @Resource
    private GroupMapper groupMapper;

    /**
     * 创建群组
     *
     * @param owner      群主userId
     * @param groupName  群组名字
     * @param desc       群描述
     * @param userIdList 成员userId(数组)
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Group> createGroup(String owner, String groupName,
                                             String desc, String... userIdList) {
        ResponseEntity<Group> responseEntity;
        try {
            // gname和desc过长会报错,这边直接写死,APP获取群组信息从本地DB获取
            CreateGroupResult createGroupResult = jMessageClient.createGroup(owner,
                    groupName, desc, "", 1, userIdList);

            Group group = new Group(owner, groupName, desc);
            String groupId = CommonUtil.generateId();
            group.setGroupId(groupId);
            group.setjId(createGroupResult.getGid());

            //群主 + 组员聊天列表生成会话
            List<String> groupMemberIds = new ArrayList<>();
            groupMemberIds.add(owner);
            for (String userId : userIdList) {
                groupMemberIds.add(userId);
            }

            // 持久化DB
            // 新增群组
            groupMapper.addGroup(group);


            // 新增群组成员
            List<GroupMembers> groupMembersList = new ArrayList<>();
            for (String userId : userIdList) {
                GroupMembers groupMembers = new GroupMembers(group.getGroupId(),
                        userId, Constant.IM_GROUP_NOT_OWNER);
                groupMembersList.add(groupMembers);
            }

            // 修复一个小bug
            // 把owner加入群组中
            groupMembersList.add(new GroupMembers(group.getGroupId(),
                    owner, Constant.IM_GROUP_OWNER));
            groupMapper.addGroupMembers(groupMembersList);

            responseEntity = new ResponseEntity<>(group, HttpStatus.OK);
        } catch (APIConnectionException e) {
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(new Group(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (APIRequestException e) {
            logger.error("status: " + e.getStatus() + ",errorCode: "
                    + e.getErrorCode() + ",errorMessage: "
                    + e.getErrorMessage());
            responseEntity = new ResponseEntity<>(new Group(),
                    HttpStatus.valueOf(e.getStatus()));
        }
        return responseEntity;
    }

    /**
     * 修改群名
     *
     * @param gId       群组ID(极光)
     * @param groupName 群名
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<String> updateGroupName(String gId, String groupName) {
        ResponseEntity<String> responseEntity;
        try {
            // DB
            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("gId", Long.valueOf(gId));
            paramMap.put("groupName", groupName);
            groupMapper.updateGroupName(paramMap);
            // 极光
            GroupInfoResult groupInfo = jMessageClient.getGroupInfo(Long.valueOf(gId));
            jMessageClient.updateGroupInfo(Long.valueOf(gId), groupName, groupInfo.getDesc(), "");
            responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateGroupName error: " + e.getMessage());
            responseEntity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
