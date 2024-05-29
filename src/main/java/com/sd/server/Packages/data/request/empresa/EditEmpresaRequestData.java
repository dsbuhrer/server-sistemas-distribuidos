package com.sd.server.Packages.data.request.empresa;

import com.sd.server.Base.PackageData;
import com.sd.server.Models.Empresa;

import java.util.UUID;

public class EditEmpresaRequestData extends PackageData {

    UUID empresa_id;
    String name;
    String email;
    String password;
    String type;
    String token;

    public EditEmpresaRequestData() {
    }

    public EditEmpresaRequestData(Empresa empresa, String token) {
        empresa_id = empresa.getUuid();
        name = empresa.getRazao();
        email = empresa.getEmail();
        password = empresa.getSenha();
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getEmpresa_id() {
        return empresa_id.toString();
    }

    public void setEmpresa_id(String empresa_id) {
        this.empresa_id = UUID.fromString(empresa_id);
    }
}
