package org.example.shop.domain.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Identifiable<ID>, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean exists(ID id);

    void deleteById(ID id);

    void deleteAll();

    int count();
}