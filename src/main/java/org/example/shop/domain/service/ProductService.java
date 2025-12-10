package org.example.shop.domain.service;

import org.example.shop.domain.model.Product;
import org.example.shop.domain.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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

        if (price == null || price < 0) {
            throw new IllegalArgumentException("price < 0");
        }

        if (weightKg == null || weightKg < 0) {
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

    public Product update(Integer id, String code, String name, Float price,
                          Float weightKg, Integer L, Integer W, Integer H, String description) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code is blank");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is blank");
        }

        if (price == null || price < 0) {
            throw new IllegalArgumentException("price < 0");
        }

        if (weightKg == null || weightKg < 0) {
            throw new IllegalArgumentException("weight < 0");
        }

        Optional<Product> product = products.findById(id);
        String curCode = String.valueOf(product.map(Product::getCode).orElse(null));

        if (!curCode.equals(code)) {
            products.findByCode(code).ifPresent(c -> {
                throw new IllegalArgumentException("code already exists");
            });
        }

        Product p = new Product();
        p.setId(id);
        p.setCode(code.trim());
        p.setName(name.trim());
        p.setPrice(price);
        p.setWeightKg(weightKg);
        p.setLengthCm(L);
        p.setWidthCm(W);
        p.setHeightCm(H);
        p.setDescription(description == null ? "" : description);
        return products.update(p);
    }

    public List<Product> list() {
        return products.findAll();
    }

    public Product findById(Integer id) {
        Optional<Product> product = products.findById(id);
        if (product.isEmpty()) {
            throw new NoSuchElementException("product not found");
        }
        return product.get();
    }

    public void remove(Integer id) {
        Optional<Product> product = products.findById(id);
        if (product.isEmpty()) {
            throw new NoSuchElementException("product not found");
        }

        products.deleteById(id);
    }
}
