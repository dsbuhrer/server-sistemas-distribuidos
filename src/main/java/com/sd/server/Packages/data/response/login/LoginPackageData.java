package com.sd.server.Packages.data.response.login;

import com.sd.server.Base.PackageData;

public class LoginPackageData extends PackageData {
    private String token;

    public LoginPackageData(String token) {
        this.token = token;
    }

    public LoginPackageData() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
