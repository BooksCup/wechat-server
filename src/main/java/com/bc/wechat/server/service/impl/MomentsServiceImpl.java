package com.bc.wechat.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.bc.wechat.server.entity.Moments;
import com.bc.wechat.server.entity.MomentsComment;
import com.bc.wechat.server.entity.User;
import com.bc.wechat.server.mapper.MomentsMapper;
import com.bc.wechat.server.service.MomentsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 朋友圈业务类实现类
 *
 * @author zhou
 */
@Service("momentsService")
public class MomentsServiceImpl implements MomentsService {

    @Resource
    private MomentsMapper momentsMapper;

    /**
     * 新增朋友圈实体
     *
     * @param moments 朋友圈实体
     */
    @Override
    public void addMoments(Moments moments) {
        momentsMapper.addMoments(moments);
    }

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 朋友圈列表
     */
    @Override
    public List<Moments> getMomentsListByUserId(Map<String, Object> paramMap) {
        return momentsMapper.getMomentsListByUserId(paramMap);
    }

    /**
     * 获取点赞用户列表
     *
     * @param momentsId 朋友圈ID
     * @return 点赞用户列表
     */
    @Override
    public List<User> getLikeUserListByMomentsId(String momentsId) {
        return momentsMapper.getLikeUserListByMomentsId(momentsId);
    }

    /**
     * 获取最近n张朋友圈图片
     *
     * @param userId 用户ID
     * @return 最近n张朋友圈图片
     */
    @Override
    public List<String> getLastestMomentsPhotosByUserId(String userId) {
        List<String> resultList = new ArrayList<>();
        List<String> lastestCirclePhotoList = momentsMapper.getLastestMomentsPhotosByUserId(userId);
        if (!CollectionUtils.isEmpty(lastestCirclePhotoList)) {
            for (String lastestCirclePhoto : lastestCirclePhotoList) {
                List<String> photoes;
                try {
                    photoes = JSONArray.parseArray(lastestCirclePhoto, String.class);
                    if (null == photoes) {
                        photoes = new ArrayList<>();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    photoes = new ArrayList<>();
                }
                for (String photo : photoes) {
                    resultList.add(photo);
                    if (resultList.size() >= 4) {
                        break;
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 点赞
     *
     * @param paramMap 参数map
     */
    @Override
    public void likeMoments(Map<String, Object> paramMap) {
        momentsMapper.likeMoments(paramMap);
    }

    /**
     * 取消点赞
     *
     * @param paramMap 参数map
     */
    @Override
    public void unLikeMoments(Map<String, Object> paramMap) {
        momentsMapper.unLikeMoments(paramMap);
    }

    /**
     * 获取某个朋友圈下的评论
     *
     * @param momentsId 朋友圈ID
     * @return 评论列表
     */
    @Override
    public List<MomentsComment> getMomentsCommentListByMomentsId(String momentsId) {
        return momentsMapper.getMomentsCommentListByMomentsId(momentsId);
    }

    /**
     * 朋友圈发表评论
     *
     * @param momentsComment 朋友圈评论
     */
    @Override
    public void addMomentsComment(MomentsComment momentsComment) {
        momentsMapper.addMomentsComment(momentsComment);
    }

    /**
     * 查找某个用户的朋友圈列表
     *
     * @param paramMap 参数map
     * @return 某个用户的朋友圈列表
     */
    @Override
    public List<Moments> getMomentsListByPublishUserId(Map<String, Object> paramMap) {
        return momentsMapper.getMomentsListByPublishUserId(paramMap);
    }
}
