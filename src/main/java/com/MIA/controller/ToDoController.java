package com.MIA.controller;

import com.MIA.model.Task;
import com.MIA.model.User;
import com.MIA.repository.TaskRepository;
import com.MIA.repository.UserRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Component
@FxmlView("ToDo.fxml")
public class ToDoController {

    public MenuItem menuFileClose;
    public Button addTodoButton;
    public TextField todoInputTextField;
    public Label emptyTodoError;
    public VBox todosVBox;

    //TODO:ADD SUBTASK METHOD TO CODE
    public ToDoController() {
    }

    @FXML
    public void initialize() {
        //populateTodoLayout(AppState.getInstance().getLoggedInUser().getTasks());
    }

    public void populateTodoLayout(List<Task> tasks) {
        todosVBox.getChildren().clear();
        int i = 1;
        for (final Task task : tasks) {
            CheckBox checkBox = new CheckBox(i + ". " + task.getDescription());
            checkBox.setSelected(!task.isInProgress());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                task.setInProgress(!newValue);
                getTaskRepository().save(task);
                playRandomSound();
            });
            todosVBox.getChildren().add(checkBox);
            i++;
        }
    }

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void addTodo(ActionEvent actionEvent, String description) {
        Task task = new Task();
        task.setCreatedAt(new Date());
        task.setDescription(description);
        task.setInProgress(true);
        //task.setUser(AppState.getInstance().getLoggedInUser());
        getTaskRepository().save(task);

        //User user = getUserRepository().findByUsername(AppState.getInstance().getLoggedInUser().getUsername());
        //populateTodoLayout(user.getTasks());
    }

    private TaskRepository getTaskRepository() {
        return null;
    }

    private UserRepository getUserRepository() {
        return null;
    }

    public void addToDo(ActionEvent event) {
        emptyTodoError.setVisible(false);
        if (todoInputTextField.getText().equals("")) {
            emptyTodoError.setVisible(true);
        } else {
            addTodo(event, todoInputTextField.getText());
        }
    }

    private void playRandomSound() {
        String ssound = Paths.get("pencil.mp3").toUri().toString();
        Media sound = new Media(ssound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
