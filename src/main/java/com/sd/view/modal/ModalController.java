package com.sd.view.modal;

import com.sd.server.Server;
import com.sd.view.App;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalController {

    public TextField port_tf;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        Integer port_value = Integer.parseInt(port_tf.getText());
        Server.start(port_value);
        App.changeScreen(getStage(port_tf),"connection_list.fxml");
    }

    public Stage getStage(Node element){
        return (Stage) element.getScene().getWindow();
    }
}