package org.example.shop.domain.model;

import org.example.shop.domain.repository.MutableIdentifiable;

public class CartItem implements MutableIdentifiable<Integer> {
    private Integer id;
    private Integer productId;
    private int quantity;

    public CartItem(Integer id, Integer productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItem(Integer productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
