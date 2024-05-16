package com.sd.server.Packages.data.request.user;

import com.sd.server.Base.PackageData;
import com.sd.server.Models.User;

public class CreateUserRequestData extends PackageData {
    String nome;
    String email;
    String senha;
    String token;

    public CreateUserRequestData() {
    }

    public CreateUserRequestData(User user, String token) {
        nome = user.getNome();
        email = user.getEmail();
        senha = user.getSenha();
        this.token = token;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return senha;
    }

    public void setPassword(String password) {
        this.senha = password;
    }

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
