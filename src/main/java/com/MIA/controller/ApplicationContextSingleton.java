package com.MIA.controller;

import com.MIA.ToDoAppApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Data;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public @Data
class ApplicationContextSingleton {
    //TODO: ADD THREADING TO CONNECTION
    private static ConfigurableApplicationContext applicationContext;
    private static ClassLoader classLoader;
    private static String username;
    private static int taskId;


    public static void initContext(String[] args) {
        if (applicationContext == null) {

            applicationContext = new SpringApplicationBuilder()
                    .sources(ToDoAppApplication.class)
                    .run(args);
            classLoader = applicationContext.getClassLoader();
        }
    }

    public static Parent createContextFromResource(String resource) {

        FXMLLoader loader = getFxmlLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(resource);
        try {
            Parent parent = loader.load(resourceAsStream);
            parent.getStylesheets().add("awesome.css");
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FXMLLoader getFxmlLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(clazz -> applicationContext.getBean(clazz));
        return loader;
    }

    public static String getLoggedInUsername() {
        return username;
    }

    public static void setLoggedInUsername(String loggedInUserName) {
        username = loggedInUserName;
    }

    public static int getTaskId() {
        return taskId;
    }

    public static void setTaskId(int taskId) {
        ApplicationContextSingleton.taskId = taskId;
    }
}
