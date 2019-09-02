package com.bc.wechat.server.entity;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
