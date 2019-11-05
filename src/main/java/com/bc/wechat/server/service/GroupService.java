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
}
