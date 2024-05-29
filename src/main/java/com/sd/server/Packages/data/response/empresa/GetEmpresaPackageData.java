package com.sd.server.Packages.data.response.empresa;

import com.sd.server.Base.PackageData;
import com.sd.server.Models.Empresa;

import java.util.List;

public class GetEmpresaPackageData extends PackageData {

    private List<Empresa> empresas;

    public GetEmpresaPackageData(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }
}
