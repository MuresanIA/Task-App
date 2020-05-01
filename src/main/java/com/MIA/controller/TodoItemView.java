package com.MIA.controller;

import com.MIA.model.Task;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    public Button changeUserButton;

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

    public void init(int index, Task task, ChangeListener<Boolean> listener, boolean shouldDisplayButton, EventHandler<ActionEvent> buttonHandler) {
        checkbox.setText(index + ". " + task.getDescription());
        checkbox.setSelected(!task.isInProgress());
        checkbox.selectedProperty().addListener(listener);

        changeUserButton.setVisible(shouldDisplayButton);
        changeUserButton.setOnAction(buttonHandler);
    }
}
