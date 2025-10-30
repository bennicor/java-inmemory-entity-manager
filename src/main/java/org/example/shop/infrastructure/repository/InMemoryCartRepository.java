package org.example.shop.infrastructure.repository;

import org.example.shop.domain.model.Cart;
import org.example.shop.domain.model.CartItem;
import org.example.shop.domain.repository.CartRepository;

import java.util.Objects;
import java.util.Optional;

public class InMemoryCartRepository extends InMemoryGenericRepository<Cart>
        implements CartRepository {
    private int cartItemId = 1;

    @Override
    public Cart save(Cart cart) {
        super.save(cart);

        for (CartItem ci : cart.getItems()) {
            if (ci.getId() == null) {
                ci.setId(cartItemId++);
            }
        }
        return cart;
    }

    public Optional<Cart> findByCustomerId(Integer customerId) {
        return findAll().stream().filter(c -> Objects.equals(c.getCustomerId(), customerId)).findFirst();
    }
}
