package org.example.shop.domain.repository;

import java.util.Optional;
import java.util.List;

public interface CrudRepository<T extends Identifiable<ID>, ID> {
    ID save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean exists(ID id);
    void deleteById(ID id);
    void deleteAll();
    int count();
}