package com.sd.server.Packages.data.request.empresa;

import com.sd.server.Base.PackageData;

public class GetEmpresaRequestData extends PackageData {
    private String token;
    public GetEmpresaRequestData(){
    }

    public GetEmpresaRequestData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
