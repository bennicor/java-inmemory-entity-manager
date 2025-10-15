package org.example.shop.domain.service;

import org.example.shop.domain.model.Cart;
import org.example.shop.domain.model.CartItem;
import org.example.shop.domain.repository.CartRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CartService {
    private final CartRepository carts;

    public CartService(CartRepository carts) {
        this.carts = carts;
    }

    public Cart getOrCreate(Integer customerId){
        return carts.findByCustomerId(customerId).orElseGet(() -> {
            Cart c = new Cart();
            c.setCustomerId(customerId);
            return carts.save(c);
        });
    }

    public Cart view(Integer customerId){
        return getOrCreate(customerId);
    }

    public void add(Integer customerId, Integer productId, int quantity){
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity <= 0");
        }

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

    public void set(Integer customerId, Integer productId, int quantity){
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity <= 0");
        }

        Cart cart = getOrCreate(customerId);
        cart.getItems().removeIf(i -> Objects.equals(i.getProductId(), productId));

        CartItem ci = new CartItem();
        ci.setProductId(productId);
        ci.setQuantity(quantity);

        cart.getItems().add(ci);
        carts.save(cart);
    }

    public void remove(Integer customerId, Integer productId){
        Cart cart = getOrCreate(customerId);
        cart.getItems().removeIf(i -> Objects.equals(i.getProductId(), productId));
        carts.save(cart);
    }

    public void clear(Integer customerId){
        Cart cart = getOrCreate(customerId);
        cart.getItems().clear();
        carts.save(cart);
    }
}
