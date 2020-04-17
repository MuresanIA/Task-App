package com.MIA.controller;

import com.MIA.model.Task;
import com.MIA.model.User;
import com.MIA.repository.TaskRepository;
import com.MIA.repository.UserRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;


public class Controller {
    @FXML
    private AnchorPane todoLayout;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
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
    private MenuItem menuItemLogin;

    @FXML
    private MenuItem mnuItemRegister;

    @FXML
    private TabPane tabPane;

    @FXML
    private AnchorPane registerLayout;
    @FXML
    private AnchorPane loginLayout;

    private UserRepository userRepository;

    private TaskRepository taskRepository;

    private boolean isConnectionSuccessful = true;

    private Tab todoTab;
    private Tab loginTab;
    private Tab registerTab;

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
        taskRepository = new TaskRepository(entityManager);
    }


    @FXML
    private void registerUser(ActionEvent actionEvent) {
        clearInfoText();

        if (txtFieldUsernameRegister.getText().equals("")) {
            updateInfoText("Username's empty. Please fill in!");
            return;
        }
        if (pwdFieldRegister.getText().equals("")) {
            updateInfoText("Password field is empty. Please fill in!");
            return;
        }
        if (pwdFieldConfirmRegister.getText().equals("")) {
            updateInfoText("Password field is empty. Please fill in!");
            return;
        }

        if (userRepository.usernameAlreadyInDB(txtFieldUsernameRegister.getText())) {
            updateInfoText("Username's already taken!");

            User user = userRepository.findByUsername(txtFieldUsernameRegister.getText());
            Set<Task> tasks = user.getTasks();
            return;
        }

        if (!pwdFieldRegister.getText().equals(pwdFieldConfirmRegister.getText())) {
            updateInfoText("Passwords don't match!");
            return;
        }

        User user = new User();
        user.setUsername(txtFieldUsernameRegister.getText());
        user.setPassword(pwdFieldRegister.getText());

        userRepository.save(user);

        if (userRepository.usernameAlreadyInDB(user.getUsername())) {
            txtFieldUsernameRegister.setText("");
            pwdFieldRegister.setText("");
            pwdFieldConfirmRegister.setText("");
            updateInfoText("Username registered successfully!");

            Task task = new Task();
            task.setDescription("Get good.");
            task.setInProgress(true);
            task.setUser(user);
            taskRepository.save(task);
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

    @FXML
    private void loginUser(ActionEvent actionEvent) {
        User user = userRepository.findByUsername(txtFieldUsernameLogin.getText());
        if (user == null) {
            lblInformationLogin.setText("Invalid username!");
            return;
        }
        if (!user.getPassword().equals(pwdFieldLogin.getText())) {
            lblInformationLogin.setText("Wrong password!");
            return;
        }


        lblInformationLogin.setText("Login successful");
        toggleTodoTab();

    }

    public void toggleTodoTab() {
        if (!todoLayout.isVisible()) {
            todoTab = createTodoTab();
            tabPane.getTabs().add(todoTab);
        } else {
            todoLayout.setVisible(false);
            tabPane.getTabs().remove(todoTab);
        }
    }

    public Tab createTodoTab() {
        Tab todoTab = new Tab();
        todoTab.setText("TODOTAB");
        todoTab.setContent(todoLayout);
        todoLayout.setVisible(true);

        return todoTab;
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

    public void toggleLoginTab(ActionEvent actionEvent) {
        if (!loginLayout.isVisible()) {
            loginTab = createLoginTab();
            menuItemLogin.setText("Hide Login");
            tabPane.getTabs().add(loginTab);
        } else {
            loginLayout.setVisible(false);
            menuItemLogin.setText("Show Login");
            tabPane.getTabs().remove(loginTab);
        }
    }


    public Tab createLoginTab() {
        Tab loginTab = new Tab();
        loginTab.setText("Login");
        loginTab.setContent(loginLayout);
        loginLayout.setVisible(true);

        return loginTab;
    }

    public void toggleRegisterTab(ActionEvent actionEvent) {
        if (!registerLayout.isVisible()) {
            registerTab = createRegisterTab();
            mnuItemRegister.setText("Hide Register");
            tabPane.getTabs().add(registerTab);

        } else {
            registerLayout.setVisible(false);
            mnuItemRegister.setText("Show Register");
            tabPane.getTabs().remove(registerTab);
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
