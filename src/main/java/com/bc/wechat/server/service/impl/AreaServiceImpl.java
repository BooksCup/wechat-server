package com.bc.wechat.server.service.impl;

import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.District;
import com.bc.wechat.server.entity.area.Province;
import com.bc.wechat.server.mapper.AreaMapper;
import com.bc.wechat.server.service.AreaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
     * 新增省
     *
     * @param province 省
     */
    @Override
    public void addProvince(Province province) {
        areaMapper.addProvince(province);
    }

    /**
     * 修改省
     *
     * @param province 省
     */
    @Override
    public void updateProvince(Province province) {
        areaMapper.updateProvince(province);
    }

    /**
     * 删除省
     *
     * @param provinceId 省ID
     */
    @Override
    public void deleteProvince(String provinceId) {
        areaMapper.deleteProvince(provinceId);
    }

    /**
     * 批量更新省排序
     *
     * @param provinceList 省列表
     */
    @Override
    public void batchUpdateProvinceSeq(List<Province> provinceList) {
        areaMapper.batchUpdateProvinceSeq(provinceList);
    }

    /**
     * 保存市
     *
     * @param city 市
     */
    @Override
    public void addCity(City city) {
        areaMapper.addCity(city);
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
     * 获取省数量
     *
     * @return 省数量
     */
    @Override
    public Long getProvinceCount() {
        return areaMapper.getProvinceCount();
    }

    /**
     * 获取省分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 省分页信息
     */
    @Override
    public PageInfo<Province> getProvincePageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Province> provinceList = areaMapper.getProvinceList();
        return new PageInfo<>(provinceList);
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
     * 修改市
     *
     * @param city 市
     */
    @Override
    public void updateCity(City city) {
        areaMapper.updateCity(city);
    }

    /**
     * 删除市
     *
     * @param cityId 市ID
     */
    @Override
    public void deleteCity(String cityId) {
        areaMapper.deleteCity(cityId);
    }

    /**
     * 批量更新市排序
     *
     * @param cityList 市列表
     */
    @Override
    public void batchUpdateCitySeq(List<City> cityList) {
        areaMapper.batchUpdateCitySeq(cityList);
    }

    /**
     * 通过省ID获取市数量
     *
     * @param provinceId 省ID
     * @return 市数量
     */
    @Override
    public Long getCityCountByProvinceId(String provinceId) {
        return areaMapper.getCityCountByProvinceId(provinceId);
    }

    /**
     * 获取市分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 市分页信息
     */
    @Override
    public PageInfo<City> getCityPageInfo(int pageNum, int pageSize, String provinceId) {
        PageHelper.startPage(pageNum, pageSize);
        List<City> cityList = areaMapper.getCityListByProvinceId(provinceId);
        return new PageInfo<>(cityList);
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
