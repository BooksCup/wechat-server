package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.Group;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 群组
 *
 * @author zhou
 */
@Api(value = "groups", tags = {"groups"})
@RestController
@RequestMapping("groups")
public class GroupController {

    @Resource
    private GroupService groupService;

    @ApiOperation(value = "创建群组", notes = "创建群组")
    @PostMapping(value = "")
    public ResponseEntity<Group> addGroup(
            @RequestParam String owner,
            @RequestParam String groupName,
            @RequestParam(required = false, defaultValue = "") String desc,
            @RequestParam String userIds) {
        if (null == groupName || StringUtils.isEmpty(groupName.trim())) {
            return new ResponseEntity<>(new Group(), HttpStatus.BAD_REQUEST);
        }

        String[] userIdList;
        try {
            userIdList = userIds.split(",");
        } catch (Exception e) {
            return new ResponseEntity<>(new Group(), HttpStatus.BAD_REQUEST);
        }

        // 人员去重,防止前端由于各种原因重复添加人员或者添加了自己
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(owner);
        for (String userId : userIdList) {
            userIdSet.add(userId);
        }
        userIdSet.remove(owner);
        userIdList = userIdSet.toArray(new String[userIdSet.size()]);

        return groupService.createGroup(owner, groupName, desc, userIdList);
    }

    @ApiOperation(value = "修改群名", notes = "修改群名")
    @PutMapping(value = "/{gId}/groupName")
    public ResponseEntity<String> updateGroup(
            @PathVariable String gId,
            @RequestParam String groupName) {
        return groupService.updateGroupName(gId, groupName);
    }

    /**
     * 添加或者移除群成员
     *
     * @param groupId       群id
     * @param addUserIds    待添加群成员userId列表(","分隔)
     * @param removeUserIds 待移除群成员userId列表(","分隔)
     * @return ResponseEntity
     */
    @ApiOperation(value = "更新群组成员", notes = "更新群组成员")
    @PostMapping(value = "/{groupId}/members")
    public ResponseEntity<String> addOrRemoveMembers(@PathVariable String groupId,
                                                     @RequestParam(required = false) String addUserIds,
                                                     @RequestParam(required = false) String removeUserIds) {
        String[] addList = null;
        if (!StringUtils.isEmpty(addUserIds)) {
            try {
                addList = addUserIds.split(",");
            } catch (Exception e) {
                return new ResponseEntity<>(ResponseMsg.ADD_USER_ID_LIST_ILLEGAL.getResponseCode(), HttpStatus.BAD_REQUEST);
            }
        }

        String[] removeList = null;
        if (!StringUtils.isEmpty(removeUserIds)) {
            try {
                removeList = removeUserIds.split(",");
            } catch (Exception e) {
                return new ResponseEntity<>(ResponseMsg.REMOVE_USER_ID_LIST_ILLEGAL.getResponseCode(), HttpStatus.BAD_REQUEST);
            }
        }

        return groupService.addOrRemoveMembers(groupId, addList, removeList);
    }
}
