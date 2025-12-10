package org.example.shop.domain.repository;


import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Identifiable<ID>, ID> {
    T save(T entity);

    T update(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    List<T> findForPage(int offset, int limit);

    boolean exists(ID id);

    void deleteById(ID id);

    void deleteAll();

    int count();
}