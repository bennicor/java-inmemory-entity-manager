package org.example.shop.domain.repository;

import org.example.shop.domain.model.Product;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByCode(String code);
}