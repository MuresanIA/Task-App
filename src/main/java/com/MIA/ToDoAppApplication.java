package com.MIA;

import com.MIA.controller.ApplicationContextSingleton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoAppApplication extends Application {
    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        ApplicationContextSingleton.initContext(args);

    }

    public static void main(String[] args) {
        Application.launch(ToDoAppApplication.class, args);
    }

    @Override
    public void start(Stage stage) {
        Parent root = ApplicationContextSingleton.createContextFromResource("welcome.fxml");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {

        Platform.exit();
    }

}
