package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.Group;
import org.springframework.http.ResponseEntity;

/**
 * 群组业务类接口
 *
 * @author zhou
 */
public interface GroupService {

    /**
     * 创建群组
     *
     * @param owner      群主userId
     * @param groupName  群组名字
     * @param desc       群描述
     * @param userIdList 成员userId(数组)
     * @return ResponseEntity
     */
    ResponseEntity<Group> createGroup(String owner, String groupName,
                                      String desc, String... userIdList);

    /**
     * 修改群名
     *
     * @param gId       群组ID(极光)
     * @param groupName 群名
     * @return ResponseEntity
     */
    ResponseEntity<String> updateGroupName(String gId, String groupName);

    /**
     * 添加或者移除群组成员
     *
     * @param groupId    群组ID
     * @param addList    添加成员ID列表
     * @param removeList 移除成员ID列表
     * @return ResponseEntity
     */
    ResponseEntity<String> addOrRemoveMembers(String groupId, String[] addList, String[] removeList);
}
