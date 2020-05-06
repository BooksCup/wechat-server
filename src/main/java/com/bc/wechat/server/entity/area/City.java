package com.bc.wechat.server.entity.area;

import com.alibaba.fastjson.annotation.JSONField;
import com.bc.wechat.server.utils.CommonUtil;

import java.util.List;

/**
 * 市
 *
 * @author zhou
 */
public class City {
    @JSONField(serialize = false)
    private String id;
    @JSONField(serialize = false)
    private String provinceId;
    private String name;
    @JSONField(serialize = false)
    private Integer seq;
    private List<District> district;

    public City() {

    }

    public City(String provinceId, String name, Integer seq) {
        this.id = CommonUtil.generateId();
        this.provinceId = provinceId;
        this.name = name;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }
}
