package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.entity.UserContact;
import com.bc.wechat.server.entity.UserContactTag;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.UserContactService;
import com.bc.wechat.server.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户联系人(通讯录)
 *
 * @author zhou
 */
@RestController
@RequestMapping("/users")
public class UserContactController {

    private static final Logger logger = LoggerFactory.getLogger(UserContactController.class);

    @Resource
    private UserService userService;

    @Resource
    private UserContactService userContactService;

    /**
     * 根据联系人用户ID获取联系人详情
     *
     * @param userId        用户ID
     * @param contactUserId 联系人用户ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "根据联系人用户ID获取联系人详情", notes = "根据联系人用户ID获取联系人详情")
    @GetMapping(value = "/{userId}/contacts/{contactUserId}")
    public ResponseEntity<User> getContactById(
            @PathVariable String userId,
            @PathVariable String contactUserId) {
        ResponseEntity<User> responseEntity;
        try {
            User contact = userService.getUserByUserId(contactUserId);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contactUserId);
            paramMap.put("status", Constant.RELA_STATUS_FRIEND);

            boolean isFriend = userContactService.checkIsFriend(paramMap);
            if (isFriend) {
                contact.setIsFriend(Constant.IS_FRIEND);
            } else {
                contact.setIsFriend(Constant.IS_NOT_FRIEND);
            }

            paramMap.clear();
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contact.getUserId());
            List<UserContact> userContactList = userContactService.getUserContactListByUserIdAndContactUserId(paramMap);

            if (!CollectionUtils.isEmpty(userContactList)) {
                UserContact userContact = userContactList.get(0);
                // 来源
                contact.setUserContactFrom(userContact.getContactFrom());

                // 备注和标签
                contact.setUserContactAlias(userContact.getContactAlias());
                contact.setUserContactTags(userContact.getContactTags());
                contact.setUserContactMobiles(userContact.getContactMobiles());
                contact.setUserContactDesc(userContact.getContactDesc());

                // 权限
                contact.setUserContactPrivacy(userContact.getPrivacy());
                contact.setUserContactHideMyPosts(userContact.getHideMyPosts());
                contact.setUserContactHideHisPosts(userContact.getHideHisPosts());

                // 星标好友
                contact.setIsStarred(userContact.getIsStarred());
                contact.setIsBlocked(userContact.getIsBlocked());
            }
            responseEntity = new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[getContactById] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除联系人
     *
     * @param userId        用户ID
     * @param contactUserId 联系人用户ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "删除联系人", notes = "删除联系人")
    @DeleteMapping(value = "/{userId}/contacts/{contactUserId}")
    public ResponseEntity<String> deleteContact(
            @PathVariable String userId,
            @PathVariable String contactUserId) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contactUserId);
            userContactService.deleteContact(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[deleteContact] error: " + e.getMessage());
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改联系人(设置备注和标签)
     *
     * @param userId         用户ID
     * @param contactUserId  联系人用户ID
     * @param contactAlias   联系人备注
     * @param contactMobiles 联系人电话号码(json格式)
     * @param contactDesc    联系人描述
     * @return @return ResponseEntity
     */
    @ApiOperation(value = "修改联系人(设置备注和标签)", notes = "修改联系人(设置备注和标签)")
    @PutMapping(value = "/{userId}/contacts/{contactUserId}")
    public ResponseEntity<String> editContact(
            @PathVariable String userId,
            @PathVariable String contactUserId,
            @RequestParam(required = false) String contactAlias,
            @RequestParam(required = false) String contactMobiles,
            @RequestParam(required = false) String contactDesc) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contactUserId);

            List<UserContact> userContactList = userContactService.getUserContactListByUserIdAndContactUserId(paramMap);

            UserContact userContact = new UserContact(userId, contactUserId, contactAlias, contactMobiles, contactDesc);

            if (CollectionUtils.isEmpty(userContactList)) {
                // 用户关系不存在
                // 非好友
                // insert
                userContact.setStatus(Constant.RELA_STATUS_STRANGER);
                userContactService.addUserContact(userContact);
            } else {
                // 用户关系存在
                // update
                userContact.setContactId(userContactList.get(0).getContactId());
                userContactService.updateUserContact(userContact);
            }
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[editContact] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.MODIFY_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 设置朋友权限
     *
     * @param userId        用户ID
     * @param contactUserId 联系人用户ID
     * @param privacy       朋友权限
     * @param hideMyPosts   不让他看我
     * @param hideHisPosts  不看他
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "设置朋友权限", notes = "设置朋友权限")
    @PutMapping(value = "/{userId}/contacts/{contactUserId}/privacy")
    public ResponseEntity<String> setContactPrivacy(
            @PathVariable String userId,
            @PathVariable String contactUserId,
            @RequestParam(required = false) String privacy,
            @RequestParam(required = false) String hideMyPosts,
            @RequestParam(required = false) String hideHisPosts) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contactUserId);

            List<UserContact> userContactList = userContactService.getUserContactListByUserIdAndContactUserId(paramMap);

            if (!CollectionUtils.isEmpty(userContactList)) {
                UserContact userContact = new UserContact(userContactList.get(0).getContactId(), privacy, hideMyPosts, hideHisPosts);
                userContactService.saveContactPrivacy(userContact);
            }
            responseEntity = new ResponseEntity<>(ResponseMsg.SET_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[setContactPrivacy] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.SET_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 设置或取消星标朋友
     *
     * @param userId        用户ID
     * @param contactUserId 联系人用户ID
     * @param isStarred     是否星标好友 "0":否 "1":"是"
     * @return ResponseEntity
     */
    @ApiOperation(value = "设置或取消星标朋友", notes = "设置或取消星标朋友")
    @PutMapping(value = "/{userId}/contacts/{contactUserId}/star")
    public ResponseEntity<String> setContactStarred(
            @PathVariable String userId,
            @PathVariable String contactUserId,
            @RequestParam String isStarred) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contactUserId);
            paramMap.put("isStarred", isStarred);

            userContactService.setContactStarred(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.SET_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[setContactStarred] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.SET_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 设置或取消加入黑名单
     *
     * @param userId        用户ID
     * @param contactUserId 联系人用户ID
     * @param isBlocked     是否加入黑名单 "0":否 "1":是
     * @return ResponseEntity
     */
    @ApiOperation(value = "设置或取消加入黑名单", notes = "设置或取消加入黑名单")
    @PutMapping(value = "/{userId}/contacts/{contactUserId}/block")
    public ResponseEntity<String> setContactBlocked(
            @PathVariable String userId,
            @PathVariable String contactUserId,
            @RequestParam String isBlocked) {
        ResponseEntity<String> responseEntity;
        try {
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contactUserId);
            paramMap.put("isBlocked", isBlocked);

            userContactService.setContactBlocked(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.SET_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[setContactBlocked] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.SET_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 保存用户联系人标签
     *
     * @param userId        用户ID
     * @param contactUserId 联系人用户ID
     * @param contactTags   联系人标签(json格式)
     * @param tags          用户所有标签(json格式)
     * @return ResponseEntity
     */
    @ApiOperation(value = "保存用户联系人标签", notes = "保存用户联系人标签")
    @PostMapping(value = "/{userId}/contacts/{contactUserId}/tags")
    public ResponseEntity<String> saveUserContactTags(
            @PathVariable String userId,
            @RequestParam String contactUserId,
            @RequestParam String contactTags,
            @RequestParam String tags) {
        ResponseEntity<String> responseEntity;
        try {
            // 保存联系人标签
            List<String> contactTagList;
            try {
                contactTagList = JSON.parseArray(contactTags, String.class);
            } catch (Exception e) {
                e.printStackTrace();
                contactTagList = new ArrayList<>();
            }

            List<UserContactTag> userContactTagList = new ArrayList<>();
            for (String contactTag : contactTagList) {
                UserContactTag userContactTag = new UserContactTag(userId, contactUserId, contactTag);
                userContactTagList.add(userContactTag);
            }
            userService.batchSaveUserContactTags(userId, userContactTagList);

            // 保存标签至用户关系中
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("contactUserId", contactUserId);
            List<UserContact> userContactList = userContactService.getUserContactListByUserIdAndContactUserId(paramMap);
            UserContact userContact = new UserContact(userId, contactUserId);
            userContact.setContactTags(contactTags);
            if (CollectionUtils.isEmpty(userContactList)) {
                userContact.setStatus(Constant.RELA_STATUS_STRANGER);
                userContactService.addUserContactTags(userContact);
            } else {
                userContact.setContactId(userContactList.get(0).getContactId());
                userContactService.updateUserContactTags(userContact);
            }

            // 保存用户所有标签
            paramMap.clear();
            paramMap.put("userId", userId);
            paramMap.put("tags", tags);
            userService.saveUserTags(paramMap);
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[saveUserContactTags] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}