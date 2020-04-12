package com.MIA.repository;

import com.MIA.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;

public class UserRepository implements CrudRepository<User, Integer> {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {

        return null;
    }

    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        User user = findById(id);
        if (user != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }
    }

    public User findById(Integer id) {
        /* TODO : Add Try catch */
        try {
            User user = entityManager.find(User.class, id);
            return user;
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return null;
    }

    public boolean usernameAlreadyInDB(String username) {
        return findByUsername(username) != null;
    }

    public User findByUsername(String username) {
//        throw new NotImplementedException();
        try {
            List<User> usersList = entityManager.createQuery("SELECT u  FROM User u WHERE u.username = :username")
                    .setParameter("username", username)
                    .getResultList();

            if (usersList.size() > 0) {
                return usersList.get(0);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}


