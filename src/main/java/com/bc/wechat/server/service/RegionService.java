package com.bc.wechat.server.service;

import com.bc.wechat.server.entity.Region;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 地区
 *
 * @author zhou
 */
public interface RegionService {

    /**
     * 添加地区
     *
     * @param region 地区
     */
    void addRegion(Region region);

    /**
     * 获取地区数量
     *
     * @param paramMap 参数map,包含level和parentId
     * @return 地区数量
     */
    Long getRegionCount(Map<String, String> paramMap);

    /**
     * 获取地区分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @param paramMap 参数map,包含level和parentId
     * @return 地区分页信息
     */
    PageInfo<Region> getRegionPageInfo(int pageNum, int pageSize, Map<String, String> paramMap);

    /**
     * 删除地区
     *
     * @param regionId 地区ID
     */
    void deleteRegion(String regionId);
}
