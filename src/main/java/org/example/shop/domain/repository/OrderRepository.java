package org.example.shop.domain.repository;

import org.example.shop.domain.model.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByCustomerId(Integer customerId);
}