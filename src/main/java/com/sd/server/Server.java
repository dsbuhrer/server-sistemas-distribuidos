package com.sd.server;


import com.sd.server.DAO.EmpresaDAO;
import com.sd.server.DAO.JWTSessionDAO;
import com.sd.server.DAO.UserDAO;
import com.sd.server.Threads.Connections;
import com.sd.server.Threads.GatewayThread;

public class Server {

    public static Connections connections = new Connections("connections");
    private static final JWTSessionDAO jwtSessionDAO = new JWTSessionDAO();
    private static final UserDAO userDAO= new UserDAO();
    private static final EmpresaDAO empresaDAO= new EmpresaDAO();

    public static void start(Integer port) {
        jwtSessionDAO.deleteAllJWTSession();
        userDAO.addFirstUser();
        empresaDAO.addFirstEmpresa();
        Thread gateway = new GatewayThread(connections,port);
        gateway.start();
    }
}