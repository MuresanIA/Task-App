package com.MIA.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

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
        Parent root = ApplicationContextSingleton.createContextFromResource("login.fxml");
        root.getStylesheets().add("awesome.css");
        return new Scene(root, 600, 600);
    }

    public Scene getRegisterScene() throws Exception {
        Parent root = ApplicationContextSingleton.createContextFromResource("register.fxml");
        root.getStylesheets().add("awesome.css");
        return new Scene(root, 600, 600);
    }
}
