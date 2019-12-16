package com.bc.wechat.server.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.group.CreateGroupResult;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Group;
import com.bc.wechat.server.entity.GroupMembers;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseContent;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.mapper.GroupMapper;
import com.bc.wechat.server.service.GroupService;
import com.bc.wechat.server.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
            // 防止groupName过长
            // groupName限长64位
            // The length of group name must not more than 64 bytes.
            groupName = CommonUtil.subJimString(groupName, 64);

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
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(new Group(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (APIRequestException e) {
            e.printStackTrace();
            logger.error("status: " + e.getStatus() + ",errorCode: "
                    + e.getErrorCode() + ",errorMessage: "
                    + e.getErrorMessage());
            responseEntity = new ResponseEntity<>(new Group(),
                    HttpStatus.valueOf(e.getStatus()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(new Group(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
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
            paramMap.put("groupDesc", groupName);
            groupMapper.updateGroupName(paramMap);
            // 极光
            jMessageClient.updateGroupInfo(Long.valueOf(gId), groupName, groupName, "");
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_GROUP_NAME_SUCCESS.value(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateGroupName error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.UPDATE_GROUP_NAME_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 添加或者移除群组成员
     *
     * @param groupId    群组ID
     * @param addList    添加成员ID列表
     * @param removeList 移除成员ID列表
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<String> addOrRemoveMembers(String groupId, String[] addList, String[] removeList) {
        ResponseEntity<String> responseEntity;
        try {
            // 添加或移除DB里的群成员
            List<String> addMemberList = new ArrayList<>();
            List<String> removeMemberList = new ArrayList<>();
            if (null != addList && addList.length > 0) {
                for (String addMember : addList) {
                    addMemberList.add(addMember);
                }
            }
            if (null != removeList && removeList.length > 0) {
                for (String removeMember : removeList) {
                    removeMemberList.add(removeMember);
                }
            }
            Group groupInfo = groupMapper.getGroupInfo(groupId);
            List<User> currentGroupMembers = groupMapper.getGroupMembers(groupId);
            List<String> currentGroupMemberIds = new ArrayList<>();
            for (User user : currentGroupMembers) {
                currentGroupMemberIds.add(user.getUserId());
            }


            if (!CollectionUtils.isEmpty(addMemberList)) {
                // t_group_members添加成员
                List<GroupMembers> groupMembersList = new ArrayList<>();
                for (String addMember : addMemberList) {
                    //如果已存在就不添加
                    if (currentGroupMemberIds.contains(addMember)) {
                        continue;
                    }
                    GroupMembers groupMembers = new GroupMembers(
                            groupInfo.getGroupId(), addMember, Constant.IM_GROUP_NOT_OWNER);
                    groupMembersList.add(groupMembers);
                }
                if (!CollectionUtils.isEmpty(groupMembersList)) {
                    groupMapper.addGroupMembers(groupMembersList);
                }

            }
            if (!CollectionUtils.isEmpty(removeMemberList)) {

                for (String removeMember : removeMemberList) {
                    Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
                    paramMap.put("groupId", groupId);
                    paramMap.put("userId", removeMember);

                    // t_group_members移除成员
                    groupMapper.deleteGroupMembersForRemoveMembers(paramMap);

                }
            }
            // 添加或移除极光里的群成员
            jMessageClient.addOrRemoveMembers(groupInfo.getjId(), addList, removeList);

            responseEntity = new ResponseEntity<>(ResponseContent.ADD_OR_REMOVE_MEMBERS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            logger.error("addOrRemoveMembers error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseContent.ADD_OR_REMOVE_MEMBERS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (APIRequestException e) {
            e.printStackTrace();
            logger.error("addOrRemoveMembers error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseContent.ADD_OR_REMOVE_MEMBERS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


}
