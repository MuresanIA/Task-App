package com.MIA.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project")
@Data
public class Project extends ListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int project_id;

    private String name;

    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Column(name = "created_at")
    private Date createdAt;

}


