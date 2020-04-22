package com.MIA.controller;

import com.MIA.AppState;
import com.MIA.model.User;
import com.MIA.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Caesar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.InputStream;

public class LoginController {
    public AnchorPane loginLayout;
    public Button btnLogin;
    public TextField txtFieldUsernameLogin;
    public Label lblUsername;
    public PasswordField pwdFieldLogin;
    public Button btnShowPassword;
    public TextField txtFieldPasswordShow;
    public Label lblInformationLogin;
    public Button btnPreviousScene;
    private UserRepository userRepository;
    private boolean isConnectionSuccessful = true;

    @FXML
    public void initialize() {
        try {
            persistenceConnection();

        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            System.out.println(ex.toString());
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getWelcomeScene());
    }

    public Scene getWelcomeScene() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("welcome.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add("awesome.css");
        return new Scene(root, 600, 600);
    }

    public Scene getToDoScene() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("ToDo.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);
        root.getStylesheets().add("awesome.css");
        return new Scene(root, 600, 600);
    }

    @FXML
    private void loginUser(ActionEvent actionEvent) throws Exception {
        User user = userRepository.findByUsername(txtFieldUsernameLogin.getText());
        if (user == null) {
            lblInformationLogin.setText("Invalid username!");
            return;
        }
        if (!Caesar.encrypt(user.getPassword(), 23, 7).equals(pwdFieldLogin.getText())) {
            lblInformationLogin.setText("Wrong password!");
            return;
        }
        lblInformationLogin.setText("Login successful");

        AppState.getInstance().setLoggedInUser(user);

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getToDoScene());

    }

    public void showPassword(ActionEvent actionEvent) {
        if (!txtFieldPasswordShow.isVisible()) {
            btnShowPassword.setText("Hide");
            txtFieldPasswordShow.setText(pwdFieldLogin.getText());
            txtFieldPasswordShow.setEditable(false);
            txtFieldPasswordShow.setVisible(true);
            pwdFieldLogin.setVisible(false);
        } else {
            btnShowPassword.setText("Show");
            txtFieldPasswordShow.setVisible(false);
            pwdFieldLogin.setVisible(true);
        }
    }


}
