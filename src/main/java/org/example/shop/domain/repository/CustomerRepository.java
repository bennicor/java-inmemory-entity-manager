package org.example.shop.domain.repository;

import org.example.shop.domain.model.Customer;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByPhone(String phone);
    Optional<Customer> findByEmail(String email);
}