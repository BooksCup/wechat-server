package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.Region;

import java.util.List;
import java.util.Map;

/**
 * 地区
 *
 * @author zhou
 */
public interface RegionMapper {
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
    long getRegionCount(Map<String, String> paramMap);

    /**
     * 获取地区列表
     *
     * @param paramMap 参数map,包含level和parentId
     * @return 地区列表
     */
    List<Region> getRegionList(Map<String, String> paramMap);
}
