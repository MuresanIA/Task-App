package com.MIA.controller;

import com.MIA.model.SubTask;
import com.MIA.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.InputStream;

public class TodoItemView extends BorderPane {
    @FXML
    public CheckBox checkbox;

    @FXML
    public Button deleteButton;

    @FXML
    public Button showSubtasks;



    public TodoItemView() {
        FXMLLoader fxmlLoader = ApplicationContextSingleton.getFxmlLoader();
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("todoitem.fxml");
            fxmlLoader.load(resourceAsStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void init(int index, Task task, TodoItemAction callback, boolean shouldDisplayButton) {
        checkbox.setText(index + ". " + task.getDescription());
        checkbox.setSelected(!task.isInProgress());
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> callback.checkBoxPressed(oldValue, newValue));

        deleteButton.setVisible(shouldDisplayButton);
        deleteButton.setOnAction((event) -> callback.onChangeUserButtonPressed());


        showSubtasks.setOnAction(event -> callback.onShowSubtasksButtonPressed(event));
    }
    public void init(int index ,SubTask subTask, TodoItemAction callback){
        checkbox.setText(index + ". " + subTask.getDescription());
        checkbox.setSelected(!subTask.isInProgress());
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> callback.checkBoxPressed(oldValue, newValue));
        showSubtasks.setVisible(false);
        deleteButton.setOnAction((event) -> callback.onChangeUserButtonPressed());
    }
}
