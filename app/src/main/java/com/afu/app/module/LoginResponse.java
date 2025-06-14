package com.afu.app.module;

import androidx.annotation.NonNull;

public class LoginResponse {

    private String token;//登录返回的用户token
    private String userid;//用户注册的userId，唯一

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
