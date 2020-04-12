package com.MIA.controller;

import com.MIA.model.User;
import com.MIA.repository.UserRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Controller {

    @FXML
    private MenuItem menuFileClose;
    @FXML
    private Label lblUsername;
    @FXML
    private Button btnShowPassword;
    @FXML
    private TextField txtFieldPasswordShow;
    @FXML
    private TextField txtFieldUsernameRegister;
    @FXML
    private PasswordField pwdFieldRegister;
    @FXML
    private PasswordField pwdFieldConfirmRegister;
    @FXML
    private TextField txtFieldUsernameLogin;
    @FXML
    private PasswordField pwdFieldLogin;
    @FXML
    private Label lblInformationLogin;
    @FXML
    private Label lblInformationRegister;

    @FXML
    private TabPane tabPane;

    @FXML
    private AnchorPane registerLayout;
    @FXML
    private AnchorPane loginLayout;

    private UserRepository userRepository;

    private boolean isConnectionSuccessful = true;

    public void initialize() {
        try {
            persistenceConnection();

        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        userRepository = new UserRepository(entityManager);
    }


    @FXML
    private void registerUser(ActionEvent actionEvent) {
        clearInfoText();

        if (pwdFieldRegister.getText().equals(pwdFieldConfirmRegister.getText())) {
            if (!userRepository.usernameAlreadyInDB(txtFieldUsernameRegister.getText())) {
                User user = new User();
                user.setUsername(txtFieldUsernameRegister.getText());
                user.setPassword(pwdFieldRegister.getText());
                userRepository.save(user);

                if (userRepository.usernameAlreadyInDB(user.getUsername())) {
                    updateInfoText("Username registered successfully!");
                } else {
                    updateInfoText("Registration Failed!");
                }
            } else {
                updateInfoText("Username's already taken!");
            }
        } else {
            updateInfoText("Passwords don't match!");
        }
    }


    private void clearInfoText() {
        updateInfoText("");
    }

    private void updateInfoText(String message) {
        lblInformationRegister.setText(message);
    }

    @FXML
    private void loginUser(ActionEvent actionEvent) {

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

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void showLoginTab(ActionEvent actionEvent) {
        if (!loginLayout.isVisible()) {
            tabPane.getTabs().add(createLoginTab());
        }
    }


    public Tab createLoginTab() {
        Tab loginTab = new Tab();
        loginTab.setText("Login");
        loginTab.setContent(loginLayout);
        loginLayout.setVisible(true);

        return loginTab;
    }

    public void showRegisterTab(ActionEvent actionEvent) {
        if (!registerLayout.isVisible()) {
            tabPane.getTabs().add(createRegisterTab());
        }
    }

    public Tab createRegisterTab() {
        Tab registerTab = new Tab();
        registerTab.setText("Register");
        registerTab.setContent(registerLayout);
        registerLayout.setVisible(true);

        return registerTab;
    }
}