package org.example.shop.domain.model;

import org.example.shop.domain.repository.MutableIdentifiable;

public class OrderItem implements MutableIdentifiable<Integer> {
    private Integer id;
    private Integer productId;
    private String productNameSnapshot;
    private Float unitPrice;
    private int quantity;

    public OrderItem(Integer id, Integer productId, String productNameSnapshot, Float unitPrice, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productNameSnapshot = productNameSnapshot;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderItem(Integer productId, String productNameSnapshot, Float unitPrice, int quantity) {
        this.productId = productId;
        this.productNameSnapshot = productNameSnapshot;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderItem() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductNameSnapshot() { return productNameSnapshot; }
    public void setProductNameSnapshot(String productNameSnapshot) { this.productNameSnapshot = productNameSnapshot; }

    public Float getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Float unitPrice) { this.unitPrice = unitPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
