package org.example.shop.domain.service;

import org.example.shop.domain.model.*;
import org.example.shop.domain.repository.CustomerRepository;
import org.example.shop.domain.repository.ProductRepository;
import org.example.shop.domain.repository.OrderRepository;
import org.example.shop.domain.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderService {
    private final OrderRepository orders;
    private final CustomerRepository customers;
    private final ProductRepository products;
    private final CartRepository carts;

    public OrderService(OrderRepository orders, CustomerRepository customers,
                        ProductRepository products, CartRepository carts) {
        this.orders = orders;
        this.customers = customers;
        this.products = products;
        this.carts = carts;
    }

    public Order createOrderFromCart(Integer customerId, Float deliveryCost, String paymentMethod) {
        if (deliveryCost == null || Math.signum(deliveryCost) < 0) {
            throw new IllegalArgumentException("deliveryCost < 0");
        }

        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("paymentMethod is blank");
        }

        if (customers.findById(customerId).isEmpty()) {
            throw new NoSuchElementException("customer not found");
        }

        Cart cart = carts.findByCustomerId(customerId).orElseThrow(
                () -> new IllegalArgumentException("customer has no cart")
        );

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("cart is empty");
        }

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryCost(deliveryCost);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("NEW");

        List<OrderItem> items = new ArrayList<>();
        Float totalOrderPrice = 0f;
        for (CartItem ci : cart.getItems()) {
            Product p = products.findById(ci.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("product not found: " + ci.getProductId()));

            if (ci.getQuantity() <= 0) {
                continue;
            }

            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductNameSnapshot(p.getName());
            oi.setUnitPrice(p.getPrice());
            oi.setQuantity(ci.getQuantity());
            items.add(oi);

            totalOrderPrice += p.getPrice();
        }

        cart.getItems().clear();
        carts.save(cart);

        if (items.isEmpty()) {
            throw new IllegalArgumentException("no valid items to order");
        }

        order.setItems(items);
        order.setTotalPrice(totalOrderPrice);

        return orders.save(order);
    }

    public void updateStatus(Integer orderId, String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("status is blank");
        }

        Order o = orders.findById(orderId).orElseThrow(() -> new NoSuchElementException("order not found"));
        o.setStatus(status);
        orders.save(o);
    }

    public List<Order> list() {
        return orders.findAll();
    }

    public List<Order> listByCustomer(Integer customerId) {
        return orders.findByCustomerId(customerId);
    }

    public Order get(Integer id) {
        return orders.findById(id).orElseThrow(() -> new NoSuchElementException("order not found"));
    }
}
