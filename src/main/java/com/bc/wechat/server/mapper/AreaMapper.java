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
     * 修改省
     *
     * @param province 省
     */
    void updateProvince(Province province);

    /**
     * 批量更新省排序
     *
     * @param provinceList 省列表
     */
    void batchUpdateProvinceSeq(List<Province> provinceList);

    /**
     * 删除省
     *
     * @param provinceId 省ID
     */
    void deleteProvince(String provinceId);

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
     * 新增市
     *
     * @param city 市
     */
    void addCity(City city);

    /**
     * 修改市
     *
     * @param city 市
     */
    void updateCity(City city);

    /**
     * 批量更新市排序
     *
     * @param cityList 市列表
     */
    void batchUpdateCitySeq(List<City> cityList);

    /**
     * 删除市
     *
     * @param cityId 市ID
     */
    void deleteCity(String cityId);

    /**
     * 通过省ID获取市列表
     *
     * @param provinceId 省ID
     * @return 市列表
     */
    List<City> getCityListByProvinceId(String provinceId);

    /**
     * 通过省ID获取市数量
     *
     * @param provinceId 省ID
     * @return 市数量
     */
    Long getCityCountByProvinceId(String provinceId);

    /**
     * 保存区县
     *
     * @param district 区县
     */
    void addDistrict(District district);

    /**
     * 修改区县
     *
     * @param district 区县
     */
    void updateDistrict(District district);

    /**
     * 批量更新区县排序
     *
     * @param districtList 区县列表
     */
    void batchUpdateDistrictSeq(List<District> districtList);

    /**
     * 删除区县
     *
     * @param districtId 区县ID
     */
    void deleteDistrict(String districtId);

    /**
     * 通过市ID获取区县列表
     *
     * @param cityId 市ID
     * @return 区县列表
     */
    List<District> getDistrictListByCityId(String cityId);

    /**
     * 通过市ID获取区县数量
     *
     * @param cityId 市ID
     * @return 区县数量
     */
    Long getDistrictCountByCityId(String cityId);

}