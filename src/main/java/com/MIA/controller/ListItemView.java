package com.MIA.controller;

import com.MIA.model.ListItem;
import com.MIA.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.InputStream;

public class ListItemView extends BorderPane {
    @FXML
    public CheckBox checkbox;

    @FXML
    public Button deleteButton;

    @FXML
    public Button showSubtasks;


    public ListItemView() {
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

    public void init(int index, ListItem listItem, TodoItemAction callback, boolean shouldDisplayButton) {
        checkbox.setText(index + ". " + listItem.getDescription());
        checkbox.setSelected(!listItem.isInProgress());
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> callback.checkBoxPressed(oldValue, newValue));

        deleteButton.setVisible(shouldDisplayButton);
        deleteButton.setOnAction((event) -> callback.onDeleteButtonPressed());

        showSubtasks.setVisible(listItem instanceof Task);
        showSubtasks.setOnAction(event -> callback.onShowSubtasksButtonPressed(event));
    }

}
