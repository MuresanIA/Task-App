package com.MIA.controller;

import com.MIA.model.Task;
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
import java.util.List;

public class RegisterController {
    public AnchorPane registerLayout;
    public TextField txtFieldUsernameRegister;
    public Button btnRegister;
    public PasswordField pwdFieldRegister;
    public PasswordField pwdFieldConfirmRegister;
    public Label lblInformationRegister;
    public Button btnPreviousScene;
    public Label registerText;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label confirmPasswordLabel;
    public Label emptyUserName;
    public Label emptyPasswordRegister;
    public Label emptyPasswordConfirmRegister;
    public Label userNameTaken;
    public Label passwordsDontMatch;
    private UserRepository userRepository;
    private boolean isConnectionSuccessful;

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

    @FXML
    private void registerUser(ActionEvent actionEvent) {
        //TODO: Clear errors Register button is pressed!
        emptyUserName.setText("");
        if (txtFieldUsernameRegister.getText().equals("")) {
            emptyUserName.setText("Username is empty!");
            return;
        }
        emptyPasswordRegister.setText("");
        if (pwdFieldRegister.getText().equals("")) {
            emptyPasswordRegister.setText("Password field is empty!");
            return;
        }
        emptyPasswordConfirmRegister.setText("");
        if (pwdFieldConfirmRegister.getText().equals("")) {
            emptyPasswordConfirmRegister.setText("Password field is empty!");
            return;
        }
        userNameTaken.setText("");
        if (userRepository.usernameAlreadyInDB(txtFieldUsernameRegister.getText())) {
            userNameTaken.setText("Username's already taken!");
            User user = userRepository.findByUsername(txtFieldUsernameRegister.getText());
            List<Task> tasks = user.getTasks();
            return;
        }
        passwordsDontMatch.setText("");
        if (!pwdFieldRegister.getText().equals(pwdFieldConfirmRegister.getText())) {
            passwordsDontMatch.setText("Passwords don't match!");
            return;
        }
        updateInfoText("");
        User user = new User();
        user.setUsername(txtFieldUsernameRegister.getText());
        user.setPassword(Caesar.encrypt(pwdFieldRegister.getText(), 3, 3)); // encrypting the password :)
        userRepository.save(user);
        if (userRepository.usernameAlreadyInDB(user.getUsername())) {
            txtFieldUsernameRegister.setText("");
            pwdFieldRegister.setText("");
            pwdFieldConfirmRegister.setText("");
            updateInfoText("Username registered successfully!");
        } else {
            updateInfoText("Registration Failed!");
        }
    }

    private void clearInfoText() {
        updateInfoText("");
    }

    private void updateInfoText(String message) {
        lblInformationRegister.setText(message);
    }
}
