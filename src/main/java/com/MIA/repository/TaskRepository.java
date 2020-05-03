package com.MIA.repository;

import com.MIA.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    public Task findById(int id);

}
