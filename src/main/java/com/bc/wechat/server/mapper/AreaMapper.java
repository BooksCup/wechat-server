package com.bc.wechat.server.mapper;

import com.bc.wechat.server.entity.area.City;
import com.bc.wechat.server.entity.area.District;
import com.bc.wechat.server.entity.area.Province;

/**
 * 地区
 *
 * @author zhou
 */
public interface AreaMapper {
    /**
     * 保存省
     *
     * @param province 省
     */
    void saveProvince(Province province);

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
}
