package com.MIA.controller;

import com.MIA.model.Task;
import com.MIA.model.User;
import com.MIA.repository.TaskRepository;
import com.MIA.repository.UserRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;


public class Controller {

    @FXML
    private Button btnAddTodo;
    @FXML
    private ScrollPane scrollPane;
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

    private User loginUser;

    private UserRepository userRepository;

    private TaskRepository taskRepository;

    private boolean isConnectionSuccessful = true;

    private Tab todoTab;
    private Tab loginTab;
    private Tab registerTab;

    public Controller() {
    }

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
            List<Task> tasks = user.getTasks();
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
        populateTodoLayout(user.getTasks());
        loginUser = user; // save the login user
    }

    public void populateTodoLayout(List<Task> tasks) {
        scrollPane.setContent(null);
        VBox vbox = new VBox();
        int i = 1;
        Collections.sort(tasks, new Comparator<Task>() {
            public int compare(Task o1, Task o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        for (Task task : tasks) {
            vbox.getChildren().add(new Label(i + ". " + task.getDescription()));
            i++;

        }

        Button addTodoButton = new Button("Add To Do");
        final TextField textField = new TextField();
        addTodoButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                addTodo(event, textField.getText());
            }
        });
        vbox.getChildren().add(addTodoButton);
        vbox.getChildren().add(textField);

        scrollPane.setContent(vbox);
    }

    public void toggleTodoTab() {
        todoTab = createTodoTab();
        tabPane.getTabs().add(todoTab);
    }

    public Tab createTodoTab() {
        Tab todoTab = new Tab();
        todoTab.setText("To Do");
        scrollPane.setVisible(true);
        todoTab.setContent(scrollPane);

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

    public void addTodo(ActionEvent actionEvent, String description) {

        Task task = new Task();
        task.setCreatedAt(new Date());
        task.setDescription(description);
        task.setInProgress(true);
        task.setUser(loginUser);
        taskRepository.save(task);

        User user = userRepository.findByUsername(loginUser.getUsername());
        populateTodoLayout(user.getTasks());
    }
}
