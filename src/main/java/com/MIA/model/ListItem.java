package com.MIA.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class ListItem {
    @Column(name = "description")
    private String description;
    @Column(name = "in_progress")
    private boolean inProgress;
}
