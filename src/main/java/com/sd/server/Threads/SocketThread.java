package com.sd.server.Threads;

import com.sd.server.DAO.JWTSessionDAO;
import com.sd.server.Actions.ActionHandler;
import com.sd.server.Packages.BasePackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;

public class SocketThread extends Thread
{
    protected Socket socket;

    private final JWTSessionDAO jwtSessionDAO = new JWTSessionDAO();
    public SocketThread (Socket socket,ThreadGroup tgp,String name)
    {
        super(tgp,name);
        this.socket = socket;
        start();
    }

    public void run()
    {
        System.out.println ("New Communication Thread Started");

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( socket.getInputStream()));

            String request;

            while ((request = in.readLine()) != null)
            {
                BasePackage response  = ActionHandler.dispatch(request,getIp());
                out.println(response.toJson());
            }

            out.close();
            in.close();
            jwtSessionDAO.deleteAllJWTSessionByIp(getIp());
            socket.close();
        }
        catch (IOException e)
        {
            jwtSessionDAO.deleteAllJWTSessionByIp(getIp());
        }
    }

    public String getIp(){
        return socket.getRemoteSocketAddress().toString().split(Pattern.quote(":"))[0].replace("/","");
    }
}
