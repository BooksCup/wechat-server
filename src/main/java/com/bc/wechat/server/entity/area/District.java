package com.bc.wechat.server.entity.area;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.bc.wechat.server.utils.CommonUtil;

/**
 * 区
 *
 * @author zhou
 */
@JSONType(orders = {"name", "postCode"})
public class District {
    @JSONField(serialize = false)
    private String id;
    @JSONField(serialize = false)
    private String cityId;
    private String name;
    private String postCode;
    @JSONField(serialize = false)
    private Float seq;

    public District() {

    }

    public District(String cityId, String name, String postCode, Float seq) {
        this.id = CommonUtil.generateId();
        this.cityId = cityId;
        this.name = name;
        this.postCode = postCode;
        this.seq = seq;
    }

    public District(String name, String postCode, Float seq) {
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

    public Float getSeq() {
        return seq;
    }

    public void setSeq(Float seq) {
        this.seq = seq;
    }
}
