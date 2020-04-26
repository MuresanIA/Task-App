package com.MIA.model;

import javax.persistence.*;

@Entity
@Table(name = "subtask")
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private int id;

    private String description;

    @ManyToOne
    private Task task;
}
