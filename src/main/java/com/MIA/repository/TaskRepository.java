package com.MIA.repository;

import com.MIA.model.Task;
import com.MIA.model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class TaskRepository implements CrudRepository<Task, Integer> {
    private EntityManager entityManager;

    public TaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Task> findAll() {
        return null;
    }

    public void save(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        Task task = findById(id);
        if (task != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(task);
            entityManager.getTransaction().commit();
        }
    }


    public Task findById(Integer id) {
        try {
            Task task = entityManager.find(Task.class, id);
            return task;
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return null;
    }
}
