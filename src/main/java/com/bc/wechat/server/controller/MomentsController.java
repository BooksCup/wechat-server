package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Moments;
import com.bc.wechat.server.entity.MomentsComment;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.enums.ResponseMsg;
import com.bc.wechat.server.service.MomentsService;
import com.bc.wechat.server.service.UserService;
import com.bc.wechat.server.utils.CommonUtil;
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
@RequestMapping("/moments")
public class MomentsController {

    private static final Logger logger = LoggerFactory.getLogger(MomentsController.class);

    @Resource
    private MomentsService momentsService;

    @Resource
    private UserService userService;

    /**
     * 发朋友圈
     *
     * @param userId  用户ID
     * @param content 朋友圈内容
     * @param photos  朋友圈图片
     * @return ResponseEntity
     */
    @ApiOperation(value = "发朋友圈", notes = "发朋友圈")
    @PostMapping(value = "")
    public ResponseEntity<String> addMoments(
            @RequestParam String userId,
            @RequestParam String content,
            @RequestParam String photos) {
        ResponseEntity<String> responseEntity;
        try {
            Moments moments = new Moments(userId, content, photos);
            momentsService.addMoments(moments);

            // 更新该用户最新n张朋友圈照片
            List<String> lastestMomentsPhotoList = momentsService.getLastestMomentsPhotosByUserId(userId);
            Map<String, String> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("userLastestMomentsPhotos", JSON.toJSONString(lastestMomentsPhotoList));
            userService.updateUserLastestMomentsPhotos(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_MOMENTS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.ADD_MOMENTS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param userId    用户ID
     * @param pageSize  每页数量
     * @param timestamp 时间戳
     * @return 朋友圈列表
     */
    @ApiOperation(value = "查找朋友圈列表", notes = "查找朋友圈列表")
    @GetMapping(value = "")
    public ResponseEntity<List<Moments>> getMomentsListByUserId(
            @RequestParam String userId,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Long timestamp) {
        long beginTimeStamp = System.currentTimeMillis();
        ResponseEntity<List<Moments>> responseEntity;
        try {
            if (0L == timestamp || null == timestamp) {
                timestamp = System.currentTimeMillis();
            }

            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("userId", userId);
            paramMap.put("pageSize", pageSize);
            paramMap.put("timestamp", timestamp);
            List<Moments> momentsList = momentsService.getMomentsListByUserId(paramMap);
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
        long endTimeStamp = System.currentTimeMillis();
        logger.info("getMomentsListByUserId, userId: " + userId + ", cost: " + (endTimeStamp - beginTimeStamp) + "ms");
        return responseEntity;
    }

    /**
     * 点赞
     *
     * @param momentsId 朋友圈ID
     * @param userId    用户ID
     * @return ResponseEntity
     */
    @PostMapping(value = "/{momentsId}/like")
    public ResponseEntity<String> likeMoments(
            @PathVariable String momentsId,
            @RequestParam String userId) {
        ResponseEntity<String> responseEntity;

        try {
            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("likeId", CommonUtil.generateId());
            paramMap.put("userId", userId);
            paramMap.put("momentsId", momentsId);
            momentsService.likeMoments(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.LIKE_MOMENTS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.LIKE_MOMENTS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 取消点赞
     *
     * @param momentsId 朋友圈ID
     * @param userId    用户ID
     * @return ResponseEntity
     */
    @DeleteMapping(value = "/{momentsId}/like")
    public ResponseEntity<String> unLikeMoments(
            @PathVariable String momentsId,
            @RequestParam String userId) {
        ResponseEntity<String> responseEntity;

        try {
            Map<String, Object> paramMap = new HashMap<>(Constant.DEFAULT_HASH_MAP_CAPACITY);
            paramMap.put("likeId", CommonUtil.generateId());
            paramMap.put("userId", userId);
            paramMap.put("momentsId", momentsId);
            momentsService.unLikeMoments(paramMap);

            responseEntity = new ResponseEntity<>(ResponseMsg.UNLIKE_MOMENTS_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.UNLIKE_MOMENTS_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 朋友圈添加评论
     *
     * @param momentsId 朋友圈ID
     * @param userId    用户ID
     * @param content   评论内容
     * @return ResponseEntity
     */
    @PostMapping(value = "/{momentsId}/comment")
    public ResponseEntity<MomentsComment> addMomentsComment(
            @PathVariable String momentsId,
            @RequestParam String userId,
            @RequestParam String content) {
        ResponseEntity<MomentsComment> responseEntity;

        try {
            MomentsComment momentsComment = new MomentsComment();
            momentsComment.setCommentId(CommonUtil.generateId());
            momentsComment.setCommentUserId(userId);
            momentsComment.setCommentMomentsId(momentsId);
            momentsComment.setCommentContent(content);
            momentsService.addMomentsComment(momentsComment);

            responseEntity = new ResponseEntity<>(momentsComment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(new MomentsComment(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除朋友圈下的某个评论
     *
     * @param commentId 评论ID
     * @return ResponseEntity
     */
    @DeleteMapping(value = "/{momentsId}/comment/{commentId}")
    public ResponseEntity<String> deleteMomentsComment(@PathVariable String commentId) {
        ResponseEntity<String> responseEntity;
        try {
            momentsService.deleteMomentsCommentByCommentId(commentId);
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_MOMENTS_COMMENT_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(ResponseMsg.DELETE_MOMENTS_COMMENT_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
