package com.sd.server.Packages.data.request.user;

import com.sd.server.Base.PackageData;

public class GetUserRequestData extends PackageData {
    private String token;
    public GetUserRequestData(){
    }

    public GetUserRequestData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
