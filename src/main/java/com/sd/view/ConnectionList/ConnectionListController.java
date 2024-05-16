package com.sd.view.ConnectionList;

import com.sd.server.DAO.JWTSessionDAO;
import com.sd.server.Models.JWTSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConnectionListController implements Initializable {

    JWTSessionDAO jwtSessionDAO = new JWTSessionDAO();
    public ListView connections_list;
    public List<JWTSession> connections_ll = new LinkedList<>();
    public ObservableList<JWTSession> connections = FXCollections.observableList(connections_ll);

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    Runnable task = this::refesh;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduler.scheduleAtFixedRate(task,0,2, TimeUnit.SECONDS);
        refesh();
    }

    public void refesh(){
        connections_ll = jwtSessionDAO.getAllSessions();
        connections.setAll(connections_ll);
        connections_list.setItems(connections);
        connections_list.refresh();
    }
}
