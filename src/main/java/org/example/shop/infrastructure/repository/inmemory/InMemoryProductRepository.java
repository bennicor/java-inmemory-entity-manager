package org.example.shop.infrastructure.repository.inmemory;

import org.example.shop.domain.model.Product;
import org.example.shop.domain.repository.ProductRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryProductRepository extends InMemoryGenericRepository<Product>
        implements ProductRepository {
    @Override
    public Optional<Product> findByCode(String code) {
        return findAll().stream().filter(p -> Objects.equals(p.getCode(), code)).findFirst();
    }
}
