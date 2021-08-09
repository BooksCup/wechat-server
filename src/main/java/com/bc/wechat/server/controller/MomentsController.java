package com.bc.wechat.server.controller;

import com.alibaba.fastjson.JSON;
import com.bc.wechat.server.cons.Constant;
import com.bc.wechat.server.entity.Moments;
import com.bc.wechat.server.entity.MomentsComment;
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
            momentsComment.setUserId(userId);
            momentsComment.setMomentsId(momentsId);
            momentsComment.setContent(content);
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
