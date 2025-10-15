package org.example.shop.domain.service;

import org.example.shop.domain.repository.CustomerRepository;
import org.example.shop.domain.model.Customer;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerRepository customers;

    public CustomerService(CustomerRepository customers) {
        this.customers = customers;
    }

    public Customer create(String fn, String mn, String ln, String phone, String email, String addr) {
        if (fn == null || fn.isBlank()) {
            throw new IllegalArgumentException("firstName is blank");
        }

        if (ln == null || ln.isBlank()) {
            throw new IllegalArgumentException("lastName is blank");
        }

        if (addr == null || addr.isBlank()) {
            throw new IllegalArgumentException("address is blank");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is blank");
        }

        customers.findByEmail(email).ifPresent(c -> {
            throw new IllegalArgumentException("email already exists");
        });

        Customer c = new Customer();
        c.setFirstName(fn.trim());
        c.setMiddleName(mn == null ? "" : mn.trim());
        c.setLastName(ln.trim());
        c.setPhone(phone == null ? "" : phone.trim());
        c.setEmail(email.trim());
        c.setAddress(addr.trim());
        return customers.save(c);
    }

    public List<Customer> list() {
        return customers.findAll();
    }

    public Customer get(Integer id) {
        Optional<Customer> customer = customers.findById(id);
        if (customer.isEmpty()) {
            throw new NoSuchElementException("customer not found");
        }
        return customer.get();
    }
}
