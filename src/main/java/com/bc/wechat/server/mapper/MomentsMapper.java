package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.Moments;
import com.bc.wechat.server.entity.MomentsComment;
import com.bc.wechat.server.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 朋友圈dao
 *
 * @author zhou
 */
public interface MomentsMapper {

    /**
     * 新增朋友圈实体
     *
     * @param moments 朋友圈实体
     */
    void addMoments(Moments moments);


    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 朋友圈列表
     */
    List<Moments> getMomentsListByUserId(Map<String, Object> paramMap);

    /**
     * 获取点赞用户列表
     *
     * @param momentsId 朋友圈ID
     * @return 点赞用户列表
     */
    List<User> getLikeUserListByMomentsId(String momentsId);

    /**
     * 获取最近n张朋友圈图片
     *
     * @param userId 用户ID
     * @return 最近n张朋友圈图片
     */
    List<String> getLastestMomentsPhotosByUserId(String userId);

    /**
     * 点赞
     *
     * @param paramMap 参数map
     */
    void likeMoments(Map<String, Object> paramMap);

    /**
     * 取消点赞
     *
     * @param paramMap 参数map
     */
    void unLikeMoments(Map<String, Object> paramMap);

    /**
     * 获取某个朋友圈下的评论
     *
     * @param momentsId 朋友圈ID
     * @return 评论列表
     */
    List<MomentsComment> getMomentsCommentListByMomentsId(String momentsId);

    /**
     * 朋友圈发表评论
     *
     * @param momentsComment 朋友圈评论
     */
    void addMomentsComment(MomentsComment momentsComment);

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 某个用户的朋友圈列表
     */
    List<Moments> getMomentsListByPublishUserId(Map<String, Object> paramMap);
}
