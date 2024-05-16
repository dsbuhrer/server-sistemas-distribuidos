package com.sd.server.Packages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasePackage {
    private static final Logger logger = LogManager.getLogger(BasePackage.class);

    private String operacao;

    private Map<String, Object> data;

    private Integer status;

    private String mensagem;

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Integer isStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public BasePackage() {
        // Default constructor
    }

    public static BasePackage simpleFromJson(String json) throws JsonProcessingException {
        logger.info("Recebido: "+json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Map<String, Object> data = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        BasePackage basePackage = new BasePackage();
        basePackage.setData(data);
        return basePackage;
    }

    public BasePackage(String operacao, Integer status) {
        this.operacao = operacao;
        this.status = status;
    }

    public BasePackage(String operacao, Map<String, Object> data, Integer status, String mensagem) {
        this.operacao = operacao;
        this.data = data;
        this.status = status;
        this.mensagem = mensagem;
    }

    public BasePackage(String operacao, Integer status, String mensagem) {
        this.operacao = operacao;
        this.status = status;
        this.mensagem = mensagem;
    }

    ObjectMapper jackson = new ObjectMapper();

    public static BasePackage fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String,Object>>() {});
        BasePackage basePackage = new BasePackage();
        basePackage.setOperacao((String) map.get("operacao"));
        basePackage.setData(map);
        basePackage.setStatus(200);
        basePackage.setMensagem("Operação bem sucedida");
        return basePackage;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("operacao", operacao);
        map.put("status", status);

        if (mensagem != null) {
            if (!mensagem.isEmpty() && mensagem.contains("token_")){
                mensagem = mensagem.replace("token_", "").trim();
                map.put("token", mensagem);
            } else if (mensagem.contains("no_message")){
                map.remove("mensagem");
            } else if (!mensagem.isEmpty()){
                map.put("mensagem", mensagem);
            }
        }

        if (data != null && !data.isEmpty()) {
            map.putAll(data);
        }

        logger.info("Enviado: " + mapper.writeValueAsString(map));
        return mapper.writeValueAsString(map);
    }

}
