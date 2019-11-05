package com.bc.wechat.server.controller;

import com.bc.wechat.server.entity.Group;
import com.bc.wechat.server.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
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
            @RequestParam String desc,
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

}
