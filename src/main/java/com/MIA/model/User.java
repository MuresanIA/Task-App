package com.MIA.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user") // redundant, because table will be named implicitly user
public @Data
class User {

    @Id // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO INCREMENT


    private int id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)

    @OrderBy("created_at ASC")
    private List<Task> tasks;

    @ManyToMany()
    @JoinTable(name = "working_project",
            joinColumns = @JoinColumn(name = "user_id"), //current entity -> user
            inverseJoinColumns = @JoinColumn(name = "project_id")) // "foreign" entity -> project
    private List<Project> projects;

    @Column(name = "isAdmin")
    private boolean isAdmin;

}



