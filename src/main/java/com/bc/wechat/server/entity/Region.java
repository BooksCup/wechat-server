package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 地区
 *
 * @author zhou
 */
public class Region {
    private String id;
    private String parentId;
    private String level;
    private String name;
    private String code;
    private Float seq;

    public Region() {

    }

    public Region(String parentId, String level, String name, String code) {
        this.id = CommonUtil.generateId();
        this.parentId = parentId;
        this.level = level;
        this.name = name;
        this.code = code;
    }

    public Region(String name, String code, Float seq) {
        this.name = name;
        this.code = code;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getSeq() {
        return seq;
    }

    public void setSeq(Float seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", seq=" + seq +
                '}';
    }
}
