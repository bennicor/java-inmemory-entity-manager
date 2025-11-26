package org.example.shop.infrastructure.repository.inmemory;

import org.example.shop.domain.model.Customer;
import org.example.shop.domain.repository.CustomerRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryCustomerRepository extends InMemoryGenericRepository<Customer>
        implements CustomerRepository {
    @Override
    public Optional<Customer> findByEmail(String email) {
        return findAll().stream().filter(c -> Objects.equals(c.getEmail(), email)).findFirst();
    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        return findAll().stream().filter(c -> Objects.equals(c.getPhone(), phone)).findFirst();
    }
}
