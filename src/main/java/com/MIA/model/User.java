package com.MIA.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "user") // redundant, because table will be named implicitly user
public class User {

    @Id // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO INCREMENT


    private int id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    @OrderBy("created_at ASC") // pune To Do-urile in ordine ASC sau DESC in functie de TIMESTAMP-ul din phpAdmin SQL -> daca aleg DESC
    private List<Task> tasks;
    @ManyToMany
    @JoinTable(name = "working_project",
            joinColumns = @JoinColumn(name = "user_id"), //current entity -> user
            inverseJoinColumns = @JoinColumn(name = "project_id")) // "foreign" entity -> project
    private List<Project> projects;

    @OneToOne(mappedBy = "user")
    private PendingUser pendingUser;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}



