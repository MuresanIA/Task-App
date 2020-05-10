package com.MIA.controller;

import javafx.event.ActionEvent;

public interface TodoItemAction {
    void checkBoxPressed(Boolean oldValue, Boolean newValue);

    void onDeleteButtonPressed();

    void onShowSubtasksButtonPressed(ActionEvent event);
}
