package com.sd.server.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sd.server.DAO.JWTSessionDAO;
import com.sd.server.DAO.EmpresaDAO;
import com.sd.server.Exceptions.*;
import com.sd.server.Models.Empresa;
import com.sd.server.Packages.BasePackage;
import com.sd.server.Packages.data.response.empresa.GetEmpresaPackageData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmpresaRepository {

    EmpresaDAO empresaDAO = new EmpresaDAO();

    JWTSessionDAO jwtSessionDAO = new JWTSessionDAO();

    public BasePackage create(String operacao, String create_request) throws JsonProcessingException, UnauthorizedEmpresaException, NoSessionException, EmailAlreadyUsedException {
        BasePackage request = BasePackage.fromJson(create_request);
        Map<String, Object> requestData = request.getData();

        String razao = (String) requestData.get("razaoSocial");
        String email = (String) requestData.get("email");
        String senha = (String) requestData.get("senha");
        String ramo = (String) requestData.get("ramo");
        String cnpj = (String) requestData.get("cnpj");
        String descricao = (String) requestData.get("descricao");

        if (razao == null || razao.isEmpty() || senha == null || senha.isEmpty() || email == null || email.isEmpty()|| ramo == null || ramo.isEmpty() || cnpj == null || cnpj.isEmpty() || descricao == null || descricao.isEmpty()) {
            return new BasePackage(operacao, 404, "Login ou senha inválidos!");
        }

        // Validando email
        if (!email.matches(".+@.+\\..{2,}") || email.length() < 7 || email.length() > 50) {
            return new BasePackage(operacao, 404, "Formato do e-mail inválido");
        }

        // Validando senha
        if (senha.length() < 3 || senha.length() > 8) {
            return new BasePackage(operacao, 404, "Tamanho da senha inválida");
        }

        if(empresaDAO.isEmpresaExistsByEmail(email)){
            return new BasePackage(operacao, 422, "E-mail já cadastrado");
        }

        if(empresaDAO.isEmpresaExistsByCnpj(cnpj)){
            return new BasePackage(operacao, 422, "CNPJ já cadastrado");
        }

        Empresa empresa = new Empresa(razao,email,senha,cnpj,descricao,ramo);
        empresa = empresaDAO.addEmpresa(empresa);

        String uuid = empresa.getUuid().toString();
        return new BasePackage(operacao, 201, "token_" + uuid);
    }

    public String hashEmpresaPassw(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public BasePackage find(String operacao, String find_request) throws JsonProcessingException, NoSessionException, UnauthorizedEmpresaException, NotFoundException {
        BasePackage request = BasePackage.fromJson(find_request);
        String email = (String) request.getData().get("email");

        Empresa empresa = empresaDAO.getEmpresaByEmail(email);
        if (empresa == null) {
            return new BasePackage(operacao, 404, "E-mail não encontrado");
        }
        Map<String, Object> responseDataMap = new HashMap<>();
        responseDataMap.put("razaoSocial", empresa.getRazao());
        responseDataMap.put("senha", empresa.getSenha());
        responseDataMap.put("cnpj", empresa.getCnpj());
        responseDataMap.put("descricao", empresa.getDescription());
        responseDataMap.put("ramo", empresa.getRamo());


        return new BasePackage(operacao, responseDataMap, 201, "no_message");
    }


    public BasePackage destroy(String operacao, String destroy_request) throws JsonProcessingException, NoSessionException, UnauthorizedEmpresaException {
        BasePackage request = BasePackage.fromJson(destroy_request);
        String email = (String) request.getData().get("email");
//        System.out.println("email destroy");
//        System.out.println(email);

        if (email == null) {
            return new BasePackage(operacao, 404, "E-mail não encontrado");
        }

        empresaDAO.deleteEmpresa(email);

        return new BasePackage(operacao, 201, "no_message");
    }

    public BasePackage get(String operacao, String get_request) throws JsonProcessingException, NoSessionException, UnauthorizedEmpresaException {
        BasePackage request = BasePackage.fromJson(get_request);
        String token = (String) request.getData().get("token");

        List<Empresa> empresas = empresaDAO.getAllEmpresas();
        GetEmpresaPackageData response_data = new GetEmpresaPackageData(empresas);

        // Convertendo os dados para um Map
        Map<String, Object> responseDataMap = new HashMap<>();
        responseDataMap.put("data", empresas);
        System.out.println(responseDataMap);
        return new BasePackage(operacao, responseDataMap, 200, "Sucesso");
    }


    public BasePackage update(String operacao, String update_request) throws JsonProcessingException, NoSessionException, UnauthorizedEmpresaException, NotFoundException {
        BasePackage request = BasePackage.fromJson(update_request);

        String email = (String) request.getData().get("email");

        if (email == null || email.isEmpty()) {
            return new BasePackage(operacao, 404, "E-mail não encontrado");
        }

        Empresa empresa = empresaDAO.getEmpresaByEmail(email);

        if(request.getData().containsKey("senha")) {
            String newPassword = (String) request.getData().get("senha");
            empresa.setSenha(newPassword);
        }

        if(request.getData().containsKey("ramo")) {
            empresa.setRamo((String) request.getData().get("ramo"));
        }

        if(request.getData().containsKey("cnpj")) {
            empresa.setCnpj((String) request.getData().get("cnpj"));
        }

        if(request.getData().containsKey("razaoSocial")) {
            empresa.setRazao((String) request.getData().get("razaoSocial"));
        }

        if(request.getData().containsKey("email")) {
            empresa.setEmail((String) request.getData().get("email"));
        }

        if(request.getData().containsKey("descricao")) {
            empresa.setDescription((String) request.getData().get("descricao"));
        }

//        System.out.println("new_empresa");

//        System.out.println(empresa);

        empresaDAO.updateEmpresa(empresa);

        return new BasePackage(operacao, 201, "no_message");
    }
}

