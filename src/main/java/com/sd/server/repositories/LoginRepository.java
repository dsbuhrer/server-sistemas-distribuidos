package com.sd.server.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.server.DAO.JWTSessionDAO;
import com.sd.server.DAO.UserDAO;
import com.sd.server.DAO.EmpresaDAO;
import com.sd.server.Exceptions.NotFoundException;
import com.sd.server.Exceptions.UnauthorizedUserException;
import com.sd.server.Exceptions.WrongCredentialsException;
import com.sd.server.Models.Empresa;
import com.sd.server.Models.JWTSession;
import com.sd.server.Models.User;
import com.sd.server.Packages.data.request.login.LoginRequestData;
import com.sd.server.Packages.data.request.logout.LogoutRequestData;
import com.sd.server.Packages.data.response.login.LoginPackageData;
import com.sd.server.Packages.BasePackage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;


public class LoginRepository {
    private static final Logger logger = LogManager.getLogger(BasePackage.class);

    UserDAO userDAO = new UserDAO();
    EmpresaDAO empresaDAO = new EmpresaDAO();

    public BasePackage login(String operacao, String login_request, String ip) throws JsonProcessingException, NotFoundException, UnauthorizedUserException, WrongCredentialsException {

        BasePackage request = BasePackage.fromJson(login_request);

        if (request.getData() == null) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        Map<String, Object> requestData = request.getData();
        String email = (String) requestData.get("email");
        String senha = (String) requestData.get("senha");

        // Validando email
        if (email == null || !email.matches(".+@.+\\..{2,}") || email.length() < 7 || email.length() > 50) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        // Validando senha
        if (senha == null || senha.length() < 3 || senha.length() > 8) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        // Realizando login
        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        String senhaArmazenada = user.getSenha();

        if (!senha.equals(senhaArmazenada)) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        String uuid = user.getUuid().toString();

        Map<String, Object> responseData = new HashMap<>();

        return new BasePackage(operacao, responseData, 200, "token_" + uuid);
    }

    public BasePackage loginEmpresa(String operacao, String login_request, String ip) throws JsonProcessingException, NotFoundException, UnauthorizedUserException, WrongCredentialsException {

        BasePackage request = BasePackage.fromJson(login_request);

        if (request.getData() == null) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        Map<String, Object> requestData = request.getData();
        String email = (String) requestData.get("email");
        String senha = (String) requestData.get("senha");

        // Validando email
        if (email == null || !email.matches(".+@.+\\..{2,}") || email.length() < 7 || email.length() > 50) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        // Validando senha
        if (senha == null || senha.length() < 3 || senha.length() > 8) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        // Realizando login
        Empresa empresa = empresaDAO.getEmpresaByEmail(email);

        if (empresa == null) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        String senhaArmazenada = empresa.getSenha();

        if (!senha.equals(senhaArmazenada)) {
            return new BasePackage(operacao, 401, "Login ou senha incorretos");
        }

        String uuid = empresa.getUuid().toString();

        Map<String, Object> responseData = new HashMap<>();

        return new BasePackage(operacao, responseData, 200, "token_" + uuid);
    }



    public BasePackage logout(String operacao, String logout_request) throws JsonProcessingException {
//        BasePackage request = BasePackage.fromJson(logout_request);


        // Deletar relação de usuário logado
        // jwt_session_dao.logout(token);

        return new BasePackage(operacao, 204 );
    }


    public void authUser(String password,User user) throws WrongCredentialsException {
        if(!BCrypt.checkpw(password,user.getSenha())){
            throw new WrongCredentialsException();
        }
    }
}
