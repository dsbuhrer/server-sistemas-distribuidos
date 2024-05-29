package com.sd.server.Packages.data.response.empresa;

import com.sd.server.Base.PackageData;
import com.sd.server.Models.Empresa;

public class FindEmpresaPackageData extends PackageData {
    private Empresa empresa;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public FindEmpresaPackageData() {
    }

    public FindEmpresaPackageData(Empresa empresa) {
        this.empresa = empresa;
    }
}
