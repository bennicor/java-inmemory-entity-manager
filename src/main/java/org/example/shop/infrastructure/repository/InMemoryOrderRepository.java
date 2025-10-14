package org.example.shop.infrastructure.repository;

import org.example.shop.domain.model.Order;
import org.example.shop.domain.repository.OrderRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryOrderRepository extends InMemoryGenericRepository<Order>
        implements OrderRepository {
    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        return findAll().stream()
                .filter(o -> Objects.equals(o.getCustomerId(), customerId))
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .collect(Collectors.toList());
    }
}
