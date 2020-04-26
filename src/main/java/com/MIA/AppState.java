package com.MIA;

import com.MIA.model.Project;
import com.MIA.model.User;
import com.MIA.repository.ProjectRepository;
import com.MIA.repository.SubTaskRepository;
import com.MIA.repository.TaskRepository;
import com.MIA.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AppState {
    private static AppState instance;
    private User loggedInUser;

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private SubTaskRepository subTaskRepository;
    private ProjectRepository projectRepository;
//TODO: threads to connection and show message => boolean expression

    private AppState() {
        persistenceConnection();
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }

        return instance;
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
        taskRepository = new TaskRepository(entityManager);
        subTaskRepository = new SubTaskRepository(entityManager);
        projectRepository = new ProjectRepository(entityManager);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}

