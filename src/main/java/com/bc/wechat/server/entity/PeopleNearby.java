package com.bc.wechat.server.entity;

import com.bc.wechat.server.utils.CommonUtil;

/**
 * 附近的人
 *
 * @author zhou
 */
public class PeopleNearby {
    private String id;
    private String userId;
    private String longitude;
    private String latitude;
    private String region;

    public PeopleNearby() {

    }

    public PeopleNearby(String userId, String longitude, String latitude, String region) {
        this.id = CommonUtil.generateId();
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
