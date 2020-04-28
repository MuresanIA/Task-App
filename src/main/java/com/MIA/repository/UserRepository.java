package com.MIA.repository;

import com.MIA.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    public void deleteById(Integer id);

    public User findByUsername(String username);
}


