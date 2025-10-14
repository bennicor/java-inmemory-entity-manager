package org.example.shop.domain.model;

import org.example.shop.domain.repository.MutableIdentifiable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements MutableIdentifiable<Integer> {
    private Integer id;
    private Integer customerId;
    private List<CartItem> items = new ArrayList<>();

    public Cart(Integer id, Integer customerId, List<CartItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }

    public Cart(Integer customerId, List<CartItem> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public Cart() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}
