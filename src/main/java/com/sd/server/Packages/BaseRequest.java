package com.sd.server.Packages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseRequest {
    private String operacao;
    public BaseRequest(String operacao) {
        this.operacao = operacao;
    }
    @JsonCreator
    public static BaseRequest create(@JsonProperty("operacao") String operacao) {
        return new BaseRequest(operacao);
    }
    public String getOperacao() {
        return operacao;
    }
    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}
