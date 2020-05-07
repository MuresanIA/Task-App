package com.MIA.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task")
@Data
public class Task {
    @Id  // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO INCREMENT
    private int id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @Column(name = "created_at")
    private Date createdAt;
    private String description;
    @Column(name = "in_progress")
    private boolean inProgress;
    @ManyToOne
    private Project project;
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private List<SubTask> subtasks;

}
