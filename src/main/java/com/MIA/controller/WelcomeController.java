package com.MIA.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.InputStream;

@Component
@FxmlView("welcome.fxml")
public class WelcomeController {
    public Button btnLoginWelcome;
    public Button btnRegisterWelcome;
    public Label welcomeText;


    public void navigateToRegisterScene(ActionEvent actionEvent) throws Exception {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getRegisterScene());

    }

    public void navigateToLoginScene(ActionEvent actionEvent) throws Exception {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getLoginScene());

    }

    public Scene getLoginScene() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("login.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add("awesome.css");
        return new Scene(root, 600, 600);
    }

    public Scene getRegisterScene() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("register.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add("awesome.css");
        return new Scene(root, 600, 600);
    }
}
