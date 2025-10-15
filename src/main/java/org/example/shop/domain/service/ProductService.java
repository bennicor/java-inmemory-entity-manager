package org.example.shop.domain.service;

import org.example.shop.domain.model.Product;
import org.example.shop.domain.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;


public class ProductService {
    private final ProductRepository products;

    public ProductService(ProductRepository products) {
        this.products = products;
    }

    public Product create(String code, String name, Float price,
                          Float weightKg, Integer L, Integer W, Integer H, String description) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code is blank");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is blank");
        }

        if (price == null || Math.signum(price) < 0) {
            throw new IllegalArgumentException("price < 0");
        }

        if (weightKg == null || Math.signum(weightKg) < 0) {
            throw new IllegalArgumentException("weight < 0");
        }

        products.findByCode(code).ifPresent(p -> {
            throw new IllegalArgumentException("code exists: " + code);
        });

        Product p = new Product();
        p.setCode(code.trim());
        p.setName(name.trim());
        p.setPrice(price);
        p.setWeightKg(weightKg);
        p.setLengthCm(L);
        p.setWidthCm(W);
        p.setHeightCm(H);
        p.setDescription(description == null ? "" : description);
        return products.save(p);
    }

    public List<Product> list() {
        return products.findAll();
    }

    public Product get(Integer id) {
        Optional<Product> product = products.findById(id);
        if (product.isEmpty()) {
            throw new NoSuchElementException("product not found");
        }
        return product.get();
    }
}
