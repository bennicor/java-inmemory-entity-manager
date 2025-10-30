package org.example.shop.domain.model;

import org.example.shop.domain.repository.MutableIdentifiable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order implements MutableIdentifiable<Integer> {
    private Integer id;
    private Integer customerId;
    private LocalDateTime orderDate;
    private Float deliveryCost;
    private String paymentMethod;
    private String status;
    private List<OrderItem> items = new ArrayList<>();

    public Order(Integer id, Integer customerId, LocalDateTime orderDate,
                 Float deliveryCost, String paymentMethod, String status, List<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.deliveryCost = deliveryCost;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.items = items;
    }

    public Order(Integer customerId, LocalDateTime orderDate,
                 Float deliveryCost, String paymentMethod, String status, List<OrderItem> items) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.deliveryCost = deliveryCost;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.items = items;
    }

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Float getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Float deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
