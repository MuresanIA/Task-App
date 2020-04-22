package com.MIA.controller;

import com.MIA.AppState;
import com.MIA.model.Task;
import com.MIA.model.User;
import com.MIA.repository.TaskRepository;
import com.MIA.repository.UserRepository;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;


public class ToDoController {

    @FXML
    private VBox vBox;

    @FXML
    private TabPane tabPane;

    private UserRepository userRepository;

    private TaskRepository taskRepository;

    private boolean isConnectionSuccessful = true;

    private Tab todoTab;

    public ToDoController() {
    }

    @FXML
    public void initialize() {
        try {
            persistenceConnection();
                    toggleTodoTab();
        populateTodoLayout(AppState.getInstance().getLoggedInUser().getTasks());

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

    public void populateTodoLayout(List<Task> tasks) {
        vBox.getChildren().clear();
        final ScrollPane scrollPane1 = new ScrollPane();
        final VBox vbox = new VBox();
        int i = 1;
//        Collections.sort(tasks, new Comparator<Task>() {
//            public int compare(Task o1, Task o2) {
//                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
//            }
//        }); Is anulate deoarece am folosit @OrderBy("created_at ASC") in User!
        for (final Task task : tasks) {
            CheckBox checkBox = new CheckBox(i + ". " + task.getDescription());
            checkBox.setSelected(!task.isInProgress());
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    task.setInProgress(!newValue);
                    taskRepository.save(task);
                    String ssound = Paths.get("pencil.mp3").toUri().toString();
                    Media sound = new Media(ssound);
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
            });
            vbox.getChildren().add(checkBox);
            i++;
        }
        final HBox hbox = new HBox();
        final Label label = new Label();
        label.getStyleClass().add("warning");
        label.setText("No to do here :(");
        Button addTodoButton = new Button("Add To Do");
        final TextField textField = new TextField();
        addTodoButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (textField.getText().equals("")) {
                    if (!hbox.getChildren().contains(label))
                        hbox.getChildren().add(label); //Daca field-ul de To Do este empty atunci afiseaza "Empty field" iar daca nu adauga un todo!
                } else {
                    addTodo(event, textField.getText());
                }
            }
        });
        hbox.getChildren().add(addTodoButton);
        hbox.getChildren().add(textField);
        vBox.getChildren().add(hbox);
        scrollPane1.setContent(vbox);
        vBox.getChildren().add(scrollPane1);
    }

    public void toggleTodoTab() {
        todoTab = createTodoTab();
        tabPane.getTabs().add(todoTab);
    }

    public Tab createTodoTab() {
        Tab todoTab = new Tab();
        todoTab.setText("To Do");
        vBox.setVisible(true);
        todoTab.setContent(vBox);

        return todoTab;
    }


    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void addTodo(ActionEvent actionEvent, String description) {
        Task task = new Task();
        task.setCreatedAt(new Date());
        task.setDescription(description);
        task.setInProgress(true);
        task.setUser(AppState.getInstance().getLoggedInUser());
        taskRepository.save(task);

        User user = userRepository.findByUsername(AppState.getInstance().getLoggedInUser().getUsername());
        populateTodoLayout(user.getTasks());
    }
}
