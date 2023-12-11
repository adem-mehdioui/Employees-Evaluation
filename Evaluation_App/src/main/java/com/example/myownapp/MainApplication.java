package com.example.myownapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {





        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

 
        //scene.getStylesheets().add(css);



        stage.setTitle("Log In");
        stage.setScene(scene);
        //stage.resizableProperty().setValue(Boolean.FALSE);

        stage.show();




    }


    public static void main(String[] args) {
        launch();
    }
}


