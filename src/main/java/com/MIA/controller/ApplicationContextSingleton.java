package com.MIA.controller;

import com.MIA.ToDoAppApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class ApplicationContextSingleton {
//TODO: ADD THREADING TO CONNECTION
    private static ConfigurableApplicationContext applicationContext;
    private static ClassLoader classLoader;
    private static String username;


    public static void initContext(String[] args) {
        if(applicationContext == null) {

            applicationContext = new SpringApplicationBuilder()
                    .sources(ToDoAppApplication.class)
                    .run(args);
            classLoader = applicationContext.getClassLoader();
        }
    }

    public static Parent createContextFromResource(String resource) {

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(clazz -> applicationContext.getBean(clazz));
        InputStream resourceAsStream = classLoader.getResourceAsStream(resource);
        try {
             Parent parent =  loader.load(resourceAsStream);
             parent.getStylesheets().add("awesome.css");
             return parent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLoggedInUser() {
        return username;
    }

    public static void setLoggedInUser(String loggedInUserName) {
        username = loggedInUserName;
    }
}
