package com.bc.wechat.server.controller;

import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Moments;
import com.bc.wechat.server.entity.MomentsComment;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.service.MomentsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 朋友圈
 *
 * @author zhou
 */
@RestController
@RequestMapping("/users")
public class UserMomentsController {

    private static final Logger logger = LoggerFactory.getLogger(UserMomentsController.class);

    @Resource
    MomentsService momentsService;

    /**
     * 查找某个用户的好友朋友圈列表
     *
     * @param userId    用户ID
     * @param pageSize  每页数量
     * @param timestamp 时间戳
     * @return 好友朋友圈列表
     */
    @ApiOperation(value = "查找某个用户的好友朋友圈列表", notes = "查找某个用户的好友朋友圈列表")
    @GetMapping(value = "/{userId}/friendMoments")
    public ResponseEntity<List<Moments>> getFriendMomentsListByUserId(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Long timestamp) {
        ResponseEntity<List<Moments>> responseEntity;
        try {
            if (null == timestamp || 0L == timestamp) {
                timestamp = System.currentTimeMillis();
            }

            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("pageSize", pageSize);
            paramMap.put("timestamp", timestamp);
            List<Moments> momentsList = momentsService.getFriendMomentsListByUserId(paramMap);
            for (Moments moments : momentsList) {
                List<User> likeUserList = momentsService.getLikeUserListByMomentsId(moments.getMomentsId());
                moments.setLikeUserList(likeUserList);

                List<MomentsComment> momentsCommentList =
                        momentsService.getMomentsCommentListByMomentsId(moments.getMomentsId());
                moments.setMomentsCommentList(momentsCommentList);
            }

            responseEntity = new ResponseEntity<>(momentsList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}