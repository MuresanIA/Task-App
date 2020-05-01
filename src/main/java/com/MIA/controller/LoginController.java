package com.MIA.controller;

import com.MIA.model.User;
import com.MIA.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utils.Caesar;

@Controller
@FxmlView("login.fxml")
public class LoginController {
    public AnchorPane loginLayout;
    public Button btnLogin;
    public TextField txtFieldUsernameLogin;
    public Label lblUsername;
    public PasswordField pwdFieldLogin;
    public Button btnShowPassword;
    public TextField txtFieldPasswordShow;
    public Button btnPreviousScene;
    public Label lblPassword;
    public Label loginText;
    public Label invalidUsername;
    public Label wrongPassword;
    public Label loginSuccesful;
    public Label emptyPassword;
    public Label emptyLoginField;

    @Autowired
     UserRepository userRepository;

    @FXML
    public void goBack(ActionEvent event) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getWelcomeScene());
    }

    public Scene getWelcomeScene(){
        Parent root = ApplicationContextSingleton.createContextFromResource("welcome.fxml");

        return new Scene(root, 600, 600);
    }

    public Scene getToDoScene() {
        Parent root = ApplicationContextSingleton.createContextFromResource("todo.fxml");
        return new Scene(root, 600, 600);
    }

    @FXML
    private void loginUser(ActionEvent actionEvent) {
        clearErrors();
        if (txtFieldUsernameLogin.getText().equals("")) {
            emptyLoginField.setText("Username is empty!");
            return;
        }
        User user = userRepository.findByUsername(txtFieldUsernameLogin.getText());
        if (user == null) {
            invalidUsername.setText("Invalid username!");
            return;
        }
        if (pwdFieldLogin.getText().equals("")) {
            emptyPassword.setText("Password field is empty!");
            return;
        }
        if (!Caesar.encrypt(user.getPassword(), 23, 7).equals(pwdFieldLogin.getText())) {
            wrongPassword.setText("Wrong password!");
            return;
        } else {
            loginSuccesful.setText("Login successful");
            ApplicationContextSingleton.setLoggedInUsername(user.getUsername());
        }

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getToDoScene());

    }

    public void clearErrors() {
        emptyLoginField.setText("");
        invalidUsername.setText("");
        emptyPassword.setText("");
        wrongPassword.setText("");

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

    public void clearErrors(String message) {
        System.out.println(message);
    }

    public void updateInfo() {
        System.out.println("");
    }
}
