package com.MIA.repository;

import java.util.List;

public interface CustomCrudRepository<T, K> {
    List<T> findAll(); // Read (SELECT *)

    void save(T t); // Create and Update (INSERT and UPDATE)

    void deleteById(K id); //Delete from WHERE id = "id"

    T findById(K id); // SELECT * from TABLE WHERE id = "id"
}
