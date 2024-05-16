package com.sd.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("modal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Servidor");
        stage.setScene(scene);
        stage.show();
    }
    public static void changeScreen(Stage stage,String resource){
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(resource));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}