package org.example.shop.domain.service;

import org.example.shop.domain.model.Cart;
import org.example.shop.domain.model.CartItem;
import org.example.shop.domain.model.Product;
import org.example.shop.domain.repository.CartRepository;
import org.example.shop.domain.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class CartService {
    private final CartRepository carts;
    private final ProductRepository products;

    public CartService(CartRepository carts, ProductRepository products) {
        this.carts = carts;
        this.products = products;
    }

    public Cart getOrCreate(Integer customerId) {
        return carts.findByCustomerId(customerId).orElseGet(() -> {
            Cart c = new Cart();
            c.setCustomerId(customerId);
            return carts.save(c);
        });
    }

    public Cart view(Integer customerId) {
        return getOrCreate(customerId);
    }

    public void add(Integer customerId, Integer productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity <= 0");
        }

        products.findById(productId).orElseThrow(() -> new NoSuchElementException("product not found: " + productId));

        Cart cart = getOrCreate(customerId);
        List<CartItem> items = cart.getItems();
        Optional<CartItem> existing = items.stream().filter(i -> Objects.equals(i.getProductId(), productId)).findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            CartItem ci = new CartItem();
            ci.setProductId(productId);
            ci.setQuantity(quantity);
            items.add(ci);
        }
        carts.save(cart);
    }

    public void remove(Integer customerId, Integer productId) {
        products.findById(productId).orElseThrow(() -> new NoSuchElementException("product not found: " + productId));

        Cart cart = getOrCreate(customerId);
        cart.getItems().removeIf(i -> Objects.equals(i.getProductId(), productId));
        carts.save(cart);
    }

    public void clear(Integer customerId) {
        Cart cart = getOrCreate(customerId);
        cart.getItems().clear();
        carts.save(cart);
    }
}
