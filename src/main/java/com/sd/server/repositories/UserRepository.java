package com.sd.server.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sd.server.DAO.JWTSessionDAO;
import com.sd.server.DAO.UserDAO;
import com.sd.server.Exceptions.*;
import com.sd.server.Models.User;
import com.sd.server.Packages.data.request.user.*;
import com.sd.server.Packages.data.response.user.FindUserPackageData;
import com.sd.server.Packages.data.response.user.GetUserPackageData;
import com.sd.server.Packages.BasePackage;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class UserRepository {

    UserDAO userDAO = new UserDAO();

    JWTSessionDAO jwtSessionDAO = new JWTSessionDAO();

    public BasePackage create(String operacao, String create_request) throws JsonProcessingException, UnauthorizedUserException, NoSessionException, EmailAlreadyUsedException {
        BasePackage request = BasePackage.fromJson(create_request);
        Map<String, Object> requestData = request.getData();

        String nome = (String) requestData.get("nome");
        String email = (String) requestData.get("email");
        String senha = (String) requestData.get("senha");

        if (nome == null || nome.isEmpty() || senha == null || senha.isEmpty() || email == null || email.isEmpty()) {
            return new BasePackage(operacao, 404, "Login ou senha inválidos!");
        }

        // Validando email
        if (!email.matches(".+@.+\\..{2,}") || email.length() < 7 || email.length() > 50) {
            return new BasePackage(operacao, 404, "Login ou senha incorretos");
        }

        // Validando senha
        if (senha.length() < 3 || senha.length() > 8) {
            return new BasePackage(operacao, 404, "Login ou senha incorretos");
        }

        if(userDAO.isUserExistsByEmail(email)){
            return new BasePackage(operacao, 422, "E-mail já cadastrado");
        }

        User user = new User(nome,email,senha);
        user = userDAO.addUser(user);

        String uuid = user.getUuid().toString();
        return new BasePackage(operacao, 201, "token_" + uuid);
    }

    public String hashUserPassw(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public BasePackage find(String operacao, String find_request) throws JsonProcessingException, NoSessionException, UnauthorizedUserException, NotFoundException {
        BasePackage request = BasePackage.fromJson(find_request);
        String email = (String) request.getData().get("email");

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return new BasePackage(operacao, 404, "E-mail não encontrado");
        }
        Map<String, Object> responseDataMap = new HashMap<>();
        responseDataMap.put("nome", user.getNome());
        responseDataMap.put("senha", user.getSenha());


        return new BasePackage(operacao, responseDataMap, 201, "no_message");
    }


    public BasePackage destroy(String operacao, String destroy_request) throws JsonProcessingException, NoSessionException, UnauthorizedUserException {
        BasePackage request = BasePackage.fromJson(destroy_request);
        String email = (String) request.getData().get("email");

        if (email == null) {
            return new BasePackage(operacao, 404, "E-mail não encontrado");
        }

        userDAO.deleteUser(email);

        return new BasePackage(operacao, 201, "no_message");
    }

    public BasePackage get(String operacao, String get_request) throws JsonProcessingException, NoSessionException, UnauthorizedUserException {
        BasePackage request = BasePackage.fromJson(get_request);
        String token = (String) request.getData().get("token");

        List<User> users = userDAO.getAllUsers();
        GetUserPackageData response_data = new GetUserPackageData(users);

        // Convertendo os dados para um Map
        Map<String, Object> responseDataMap = new HashMap<>();
        responseDataMap.put("data", users);
        System.out.println(responseDataMap);
        return new BasePackage(operacao, responseDataMap, 200, "Sucesso");
    }


    public BasePackage update(String operacao, String update_request) throws JsonProcessingException, NoSessionException, UnauthorizedUserException, NotFoundException {
        BasePackage request = BasePackage.fromJson(update_request);
        System.out.println(update_request);
        System.out.println(request.getData());
        System.out.println(request.getData().get("nome"));
        System.out.println(request.getData().get("email"));
        System.out.println(request.getData().get("senha"));



        String email = (String) request.getData().get("email");

        if (email == null || email.isEmpty()) {
            return new BasePackage(operacao, 404, "E-mail não encontrado");
        }

        User user = userDAO.getUserByEmail(email);
        System.out.println(user);

        if(request.getData().containsKey("senha")) {
            String newPassword = (String) request.getData().get("senha");
            user.setSenha(newPassword);
        }

        if(request.getData().containsKey("nome")) {
            user.setNome((String) request.getData().get("nome"));
        }
        System.out.println("new_user");

        System.out.println(user);

        userDAO.updateUser(user);

        return new BasePackage(operacao, 201, "no_message");
    }


    public void authUser(String password,User user) throws WrongCredentialsException {
        if(!BCrypt.checkpw(password,user.getSenha())){
            throw new WrongCredentialsException();
        }
    }
}

