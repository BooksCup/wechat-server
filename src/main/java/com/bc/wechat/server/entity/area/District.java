package com.bc.wechat.server.entity.area;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 区
 *
 * @author zhou
 */
public class District {
    private String id;
    private String cityId;
    private String name;
    private String postCode;
    private Integer seq;

    public District() {

    }

    public District(String cityId, String name, String postCode, Integer seq) {
        this.id = CommonUtil.generateId();
        this.cityId = cityId;
        this.name = name;
        this.postCode = postCode;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
