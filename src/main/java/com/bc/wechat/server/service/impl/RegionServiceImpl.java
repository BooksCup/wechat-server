package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.Region;
import com.bc.wechat.server.mapper.RegionMapper;
import com.bc.wechat.server.service.RegionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 地区
 *
 * @author zhou
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService {

    @Resource
    private RegionMapper regionMapper;

    /**
     * 添加地区
     *
     * @param region 地区
     */
    @Override
    public void addRegion(Region region) {
        regionMapper.addRegion(region);
    }

    /**
     * 获取地区数量
     *
     * @param paramMap 参数map,包含level和parentId
     * @return 地区数量
     */
    @Override
    public Long getRegionCount(Map<String, String> paramMap) {
        return regionMapper.getRegionCount(paramMap);
    }

    /**
     * 获取地区分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @param paramMap 参数map,包含level和parentId
     * @return 地区分页信息
     */
    @Override
    public PageInfo<Region> getRegionPageInfo(int pageNum, int pageSize, Map<String, String> paramMap) {
        PageHelper.startPage(pageNum, pageSize);
        List<Region> regionList = regionMapper.getRegionList(paramMap);
        return new PageInfo<>(regionList);
    }
}
