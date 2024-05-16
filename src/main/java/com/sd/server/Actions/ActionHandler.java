package com.sd.server.Actions;

import com.sd.server.Packages.BasePackage;
import com.sd.server.Packages.BaseRequest;
import com.sd.server.repositories.LoginRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sd.server.repositories.UserRepository;

public class ActionHandler {

    private static final LoginRepository login_repository = new LoginRepository();
    private static final UserRepository user_repository = new UserRepository();


    public static BasePackage dispatch(String response_action,String ip) throws JsonProcessingException {
        System.out.println(response_action);
        System.out.println("response action");

        BasePackage request = BasePackage.simpleFromJson(response_action);
        String operacao = (String) request.getData().get("operacao");
        System.out.println(operacao);
        System.out.println("operacao");
        if (operacao == null || operacao.equals("")){
            return new BasePackage(operacao, 404,"Operação não encontrada");
        }
        try {
            return switch (operacao) {
                case "loginCandidato" -> login_repository.login(operacao, response_action,ip);
                case "logout" -> login_repository.logout(operacao, response_action);
                case "cadastrarCandidato" -> user_repository.create(operacao, response_action);
                case "listarCandidatos" -> user_repository.get(operacao, response_action);
                case "apagarCandidato" -> user_repository.destroy(operacao, response_action);
                case "visualizarCandidato" -> user_repository.find(operacao, response_action);
                case "atualizarCandidato" -> user_repository.update(operacao, response_action);
                default -> new BasePackage(operacao, 404, "Operação não encontrada");
            };
        }catch(Exception e){
            e.printStackTrace();
            return new BasePackage(operacao, 404,"Operação não encontrada");
        }
    }
}
