package com.MIA.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subtask")
@Data

public class SubTask extends ListItem {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "subtask_id")
    private int id;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    private Task task;

}
