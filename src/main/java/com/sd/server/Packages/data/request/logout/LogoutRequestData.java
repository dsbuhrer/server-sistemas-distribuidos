package com.sd.server.Packages.data.request.logout;

import com.sd.server.Base.PackageData;

public class LogoutRequestData extends PackageData {

    private String token;
    public LogoutRequestData(){
    }

    public LogoutRequestData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
