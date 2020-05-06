package com.MIA.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subtask")
public @Data
class SubTask {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "subtask_id")
    private int id;

    @Column(name = "in_progress")
    private boolean inProgress;

    @Column(name = "created_at")
    private Date createdAt;

    private String description;

    @ManyToOne
    private Task task;

}
