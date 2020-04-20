package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.District;
import com.bc.wechat.server.entity.area.Province;
import com.bc.wechat.server.mapper.AreaMapper;
import com.bc.wechat.server.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 地区
 *
 * @author zhou
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Resource
    AreaMapper areaMapper;

    /**
     * 保存省
     *
     * @param province 省
     */
    @Override
    public void saveProvince(Province province) {
        areaMapper.saveProvince(province);
    }

    /**
     * 保存市
     *
     * @param city 市
     */
    @Override
    public void saveCity(City city) {
        areaMapper.saveCity(city);
    }

    /**
     * 保存区
     *
     * @param district 区
     */
    @Override
    public void saveDistrict(District district) {
        areaMapper.saveDistrict(district);
    }
}
