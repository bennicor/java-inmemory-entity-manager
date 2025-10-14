package org.example.shop.infrastructure.repository;

import org.example.shop.domain.model.Cart;
import org.example.shop.domain.repository.CartRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryCartRepository extends InMemoryGenericRepository<Cart>
        implements CartRepository {
    public Optional<Cart> findByCustomerId(Integer customerId) {
        return findAll().stream().filter(c -> Objects.equals(c.getCustomerId(), customerId)).findFirst();
    }
}
