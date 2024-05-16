package com.sd.server.Packages.data.request.user;

import com.sd.server.Base.PackageData;

public class FindUserRequestData extends PackageData {
    private String token;
    private String user_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public FindUserRequestData(String token, String user_id) {
        this.token = token;
        this.user_id = user_id;
    }

    public FindUserRequestData() {
    }
}

