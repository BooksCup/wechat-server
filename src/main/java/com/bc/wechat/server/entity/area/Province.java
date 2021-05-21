package com.bc.wechat.server.entity.area;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.bc.wechat.server.utils.CommonUtil;

import java.util.List;

/**
 * 省
 *
 * @author zhou
 */
@JSONType(orders = {"name", "city"})
public class Province {

    @JSONField(serialize = false)
    private String id;
    private String name;
    @JSONField(serialize = false)
    private Float seq;
    private List<City> city;

    public Province() {

    }

    public Province(String name, Float seq) {
        this.id = CommonUtil.generateId();
        this.name = name;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSeq() {
        return seq;
    }

    public void setSeq(Float seq) {
        this.seq = seq;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

}