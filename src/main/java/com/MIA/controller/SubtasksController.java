package com.MIA.controller;

import com.MIA.model.SubTask;
import com.MIA.model.Task;
import com.MIA.repository.SubTaskRepository;
import com.MIA.repository.TaskRepository;
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
@FxmlView("subtasks.fxml")
public class SubtasksController {

    public MenuItem menuFileClose;
    public Button addTodoButton;
    public TextField todoInputTextField;
    public Label emptyTodoError;
    public VBox todosVBox;
    public Button btnPreviousScene;

    @Autowired
    SubTaskRepository subTaskRepository;

    @Autowired
    TaskRepository taskRepository;

    @FXML
    public void initialize() {
        populateTodoLayout(getTask().getSubtasks());
    }

    public void goBack(ActionEvent event) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getToDoScene());
    }
    public Scene getToDoScene() {
        Parent root = ApplicationContextSingleton.createContextFromResource("todo.fxml");
        return new Scene(root, 600, 600);
    }
    
    public Task getTask() {
        int taskId = ApplicationContextSingleton.getTaskId();
        return taskRepository.findById(taskId);
    }

    public void populateTodoLayout(List<SubTask> tasks) {
        todosVBox.getChildren().clear();
        int i = 1;
        for (final SubTask subTask : tasks) {
            TodoItemView todoItem = new TodoItemView();

            todoItem.init(i, subTask, new TodoItemAction() {
                @Override
                public void checkBoxPressed(Boolean oldValue, Boolean newValue) {
                    subTask.setInProgress(!newValue);
                    subTaskRepository.save(subTask);
                    playRandomSound();
                }

                @Override
                public void onChangeUserButtonPressed() {

                }

                @Override
                public void onShowSubtasksButtonPressed(ActionEvent event) {

                }
            });

            todoItem.setPrefWidth(todosVBox.getWidth());

            todosVBox.getChildren().add(todoItem);
            i++;
        }
    }



    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void addTodo(ActionEvent actionEvent, String description) {
        SubTask subTask = new SubTask();
        subTask.setCreatedAt(new Date());
        subTask.setDescription(description);
        subTask.setInProgress(true);
        subTask.setTask(getTask());

        subTaskRepository.save(subTask);


        populateTodoLayout(getTask().getSubtasks());
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
}
