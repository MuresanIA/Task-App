package com.MIA.controller;

import com.MIA.model.Task;
import com.MIA.model.User;
import com.MIA.repository.TaskRepository;
import com.MIA.repository.UserRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Component
@FxmlView("todo.fxml")
public class ToDoController {

    public MenuItem menuFileClose;
    public Button addTodoButton;
    public TextField todoInputTextField;
    public Label emptyTodoError;
    public VBox todosVBox;
    public Button switchToProjects;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @FXML
    public void initialize() {
        populateTodoLayout(getUser().getTasks());
    }

    public User getUser() {
        String username = ApplicationContextSingleton.getLoggedInUsername();
        return userRepository.findByUsername(username);
    }

    public void populateTodoLayout(List<Task> tasks) {
        todosVBox.getChildren().clear();
        int i = 1;
        for (final Task task : tasks) {
            ListItemView todoItem = new ListItemView();

            todoItem.init(i, task, new TodoItemAction() {
                @Override
                public void checkBoxPressed(Boolean oldValue, Boolean newValue) {
                    task.setInProgress(!newValue);
                    taskRepository.save(task);
                    playRandomSound();
                }

                @Override
                public void onDeleteButtonPressed() {
                    taskRepository.delete(task);
                    populateTodoLayout(loggedInUser().getTasks());
                }

                @Override
                public void onShowSubtasksButtonPressed(ActionEvent event) {
                    ApplicationContextSingleton.setTaskId(task.getId());

                    Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stageTheEventSourceNodeBelongs.setScene(getSubtasksScene());
                }
            }, getUser().isAdmin());

            todoItem.setPrefWidth(todosVBox.getWidth());

            todosVBox.getChildren().add(todoItem);
            i++;
        }
    }

    private Scene getSubtasksScene() {
        Parent root = ApplicationContextSingleton.createContextFromResource("subtasks.fxml");
        return new Scene(root, 600, 600);
    }

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }

    private User loggedInUser() {
        return userRepository.findByUsername(ApplicationContextSingleton.getLoggedInUsername());
    }


    public void addTodo(ActionEvent actionEvent, String description) {
        User currentUser = loggedInUser();
        Task task = new Task();
        task.setCreatedAt(new Date());
        task.setDescription(description);
        task.setInProgress(true);
        task.setUser(currentUser);
        taskRepository.save(task);


        populateTodoLayout(loggedInUser().getTasks());
    }

    public void addToDo(ActionEvent event) {
        emptyTodoError.setVisible(false);
        if (todoInputTextField.getText().equals("")) {
            emptyTodoError.setVisible(true);
        } else {
            addTodo(event, todoInputTextField.getText());
            todoInputTextField.setText("");
        }
    }

    private void playRandomSound() {
        String ssound = Paths.get("pencil.mp3").toUri().toString();
        Media sound = new Media(ssound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void switchToProjectsView(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(getProjectsScene());
    }

    private Scene getProjectsScene() {
        Parent root = ApplicationContextSingleton.createContextFromResource("project.fxml");
        return new Scene(root, 600, 600);
    }
}
