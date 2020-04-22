package com.MIA;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;

import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("welcome.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add("awesome.css");
        primaryStage.setTitle("To Do");
        Scene mainScene = new Scene(root, 600, 600);
        //        loginButton.setOnAction(b-> primaryStage.setScene(sceneVariable));
//        registerButton.setOnAction(b-> primaryStage.setScene(sceneVariable));
//        VBox layout1 = new VBox();
//        layout1.getChildren().addAll(loginButton, registerButton);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
