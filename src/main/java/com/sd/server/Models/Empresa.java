package com.sd.server.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sd.server.Packages.data.request.user.CreateUserRequestData;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 36)
    private UUID uuid;
    
    @Column
    private String email;

    @Column
    private String senha;
    
    @Column
    private String razao;

    @Column
    private String cnpj;

    @Column
    private String description;

    @Column
    private String ramo;

    
    
    public Empresa() {
    }

    public Empresa(UUID uuid, String razao, String email, String senha) {
        this.uuid = uuid;
        this.razao = razao;
        this.email = email;
        this.senha = senha;
    }

    public Empresa(String razao, String email, String senha , String cnpj, String description, String ramo) {
        this.email = email;
        this.senha = senha;
        this.razao = razao;
        this.cnpj = cnpj;
        this.description = description;
        this.ramo = ramo;
    }

    public Empresa(CreateUserRequestData data) {
        this.razao = data.getName();
        this.email = data.getEmail();
        this.senha = data.getPassword();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", razao='" + razao + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }
}
