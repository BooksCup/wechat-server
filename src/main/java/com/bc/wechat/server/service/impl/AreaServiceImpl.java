package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.District;
import com.bc.wechat.server.entity.area.Province;
import com.bc.wechat.server.mapper.AreaMapper;
import com.bc.wechat.server.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 获取省列表
     *
     * @return 省列表
     */
    @Override
    public List<Province> getProvinceList() {
        return areaMapper.getProvinceList();
    }

    /**
     * 通过省ID获取市列表
     *
     * @param provinceId 省ID
     * @return 市列表
     */
    @Override
    public List<City> getCityListByProvinceId(String provinceId) {
        return areaMapper.getCityListByProvinceId(provinceId);
    }

    /**
     * 通过市ID获取区县列表
     *
     * @param cityId 市ID
     * @return 区县列表
     */
    @Override
    public List<District> getDistrictListByCityId(String cityId) {
        return areaMapper.getDistrictListByCityId(cityId);
    }

}
