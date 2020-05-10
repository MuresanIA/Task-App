package com.MIA.controller;

import com.MIA.model.Project;
import com.MIA.repository.ProjectRepository;
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
@FxmlView("project.fxml")

public class ProjectController {


    @Autowired
    public ProjectRepository projectRepository;

    public MenuItem menuFileClose;
    public VBox vBox;
    public TextField ProjectInputTextField;
    public Button addProjectButton;
    public Label emptyTxtFieldProjectError;
    public VBox projectVBox;
    public Button btnPreviousScene;

    @FXML
    public void initialize() {
        populateProjectLayout(projectRepository.findAll());
    }

    public void goBack(ActionEvent event) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(getToDoScene());
    }

    public Scene getToDoScene() {
        Parent root = ApplicationContextSingleton.createContextFromResource("todo.fxml");
        return new Scene(root, 600, 600);
    }

    public Project getProject() {
        int projectId = ApplicationContextSingleton.getProjectId();
        return projectRepository.findById(projectId);
    }

    public void populateProjectLayout(List<Project> projects) {
        projectVBox.getChildren().clear();
        int i = 1;
        for (final Project project : projects) {
            ListItemView todoItem = new ListItemView();
            todoItem.init(i, project, new TodoItemAction() {
                @Override
                public void checkBoxPressed(Boolean oldValue, Boolean newValue) {
                    project.setInProgress(!newValue);
                    projectRepository.save(project);
                    playRandomSound();
                }

                @Override
                public void onDeleteButtonPressed() {
                    projectRepository.delete(project);
                    populateProjectLayout(projectRepository.findAll());
                }

                @Override
                public void onShowSubtasksButtonPressed(ActionEvent event) {
                    //El hombre nada action
                }
            }, true);

            todoItem.setPrefWidth(projectVBox.getWidth());

            projectVBox.getChildren().add(todoItem);
            i++;
        }
    }


    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void addProject(ActionEvent actionEvent, String description) {
        Project project = new Project();
        project.setCreatedAt(new Date());
        project.setDescription(description);
        project.setInProgress(true);

        projectRepository.save(project);


        populateProjectLayout(projectRepository.findAll());
    }

    private void playRandomSound() {
        String ssound = Paths.get("pencil.mp3").toUri().toString();
        Media sound = new Media(ssound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void addProject(ActionEvent event) {
        emptyTxtFieldProjectError.setVisible(false);
        if (emptyTxtFieldProjectError.getText().equals("")) {
            emptyTxtFieldProjectError.setVisible(true);
        } else {
            addProject(event, ProjectInputTextField.getText());
            emptyTxtFieldProjectError.setText("");
        }
    }
}









