package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.District;
import com.bc.wechat.server.entity.area.Province;

import java.util.List;

/**
 * 地区
 *
 * @author zhou
 */
public interface AreaMapper {

    /**
     * 新增省
     *
     * @param province 省
     */
    void addProvince(Province province);

    /**
     * 保存市
     *
     * @param city 市
     */
    void saveCity(City city);

    /**
     * 保存区
     *
     * @param district 区
     */
    void saveDistrict(District district);

    /**
     * 获取省列表
     *
     * @return 省列表
     */
    List<Province> getProvinceList();

    /**
     * 获取省数量
     *
     * @return 省数量
     */
    Long getProvinceCount();

    /**
     * 通过省ID获取市列表
     *
     * @param provinceId 省ID
     * @return 市列表
     */
    List<City> getCityListByProvinceId(String provinceId);

    /**
     * 通过市ID获取区县列表
     *
     * @param cityId 市ID
     * @return 区县列表
     */
    List<District> getDistrictListByCityId(String cityId);

}