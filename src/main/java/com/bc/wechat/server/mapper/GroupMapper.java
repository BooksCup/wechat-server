package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.Group;
import com.bc.wechat.server.entity.GroupMembers;
import com.bc.wechat.server.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 群组
 *
 * @author zhou
 */
public interface GroupMapper {
    /**
     * 新增群组
     *
     * @param group 群组信息
     */
    void addGroup(Group group);

    /**
     * 添加群组成员
     *
     * @param groupMembersList 群组成员
     */
    void addGroupMembers(List<GroupMembers> groupMembersList);

    /**
     * 修改群名
     *
     * @param paramMap 参数map
     */
    void updateGroupName(Map<String, Object> paramMap);

    /**
     * 获取群组信息
     *
     * @param groupId 群组id
     * @return 群组信息
     */
    Group getGroupInfo(String groupId);

    /**
     * 获取群组成员列表
     *
     * @param groupId 群组ID
     * @return 群组成员列表
     */
    List<User> getGroupMembers(String groupId);

    /**
     * 根据群组ID和成员ID删除成员
     *
     * @param paramMap 群组ID和成员ID删除成员组成的paramMap
     */
    void deleteGroupMembersForRemoveMembers(Map<String, Object> paramMap);
}
