package org.example.shop.domain.repository;

import org.example.shop.domain.model.Cart;
import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    Optional<Cart> findByCustomerId(Integer customerId);
}