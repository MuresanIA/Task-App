package com.MIA.repository;

import com.MIA.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
    public Project findById(int id);
    public List<Project> findAll();
}
