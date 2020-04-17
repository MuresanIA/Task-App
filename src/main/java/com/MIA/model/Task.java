package com.MIA.model;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id  // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO INCREMENT
    private int id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    private String description;
    @Column(name = "in_progress")
    private boolean inProgress;

    public Task() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }


}
