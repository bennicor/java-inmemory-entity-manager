package org.example.shop.infrastructure.repository;

import org.example.shop.domain.repository.MutableIdentifiable;
import org.example.shop.domain.repository.CrudRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

public class InMemoryGenericRepository<T extends MutableIdentifiable<Integer>>
        implements CrudRepository<T, Integer> {
    private final Map<Integer, T> storage = new HashMap<>();
    private int currentId = 1;

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(currentId++);
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override public Optional<T> findById(Integer id) { return Optional.ofNullable(storage.get(id)); }
    @Override public List<T> findAll() { return new ArrayList<>(storage.values()); }
    @Override public boolean exists(Integer id) { return storage.containsKey(id); }
    @Override public void deleteById(Integer id) { storage.remove(id); }
    @Override public void deleteAll() { storage.clear(); }
    @Override public int count() { return storage.size(); }
}
