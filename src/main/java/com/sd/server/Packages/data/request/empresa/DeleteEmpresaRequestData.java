package com.sd.server.Packages.data.request.empresa;

import com.sd.server.Base.PackageData;

public class DeleteEmpresaRequestData extends PackageData {
    private String token;

    private String empresa_id;

    public DeleteEmpresaRequestData(String token, String empresa_id) {
        this.token = token;
        this.empresa_id = empresa_id;
    }

    public DeleteEmpresaRequestData() {
    }

    public String getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(String empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
