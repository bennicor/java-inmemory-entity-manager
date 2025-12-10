package org.example.shop.domain.service;

import org.example.shop.domain.model.*;
import org.example.shop.domain.repository.CartRepository;
import org.example.shop.domain.repository.CustomerRepository;
import org.example.shop.domain.repository.OrderRepository;
import org.example.shop.domain.repository.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderService {
    private final OrderRepository orders;
    private final CustomerRepository customers;
    private final ProductRepository products;
    private final CartRepository carts;

    private static final String SQL_SELECT_ITEMS_BY_ORDER_ID =
            "select id, order_id, product_id, product_name_snapshot, " +
                    "unit_price, quantity from order_item where order_id=?";

    public OrderService(OrderRepository orders, CustomerRepository customers,
                        ProductRepository products, CartRepository carts) {
        this.orders = orders;
        this.customers = customers;
        this.products = products;
        this.carts = carts;
    }

    private OrderItem mapRowItem(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();
        item.setId(rs.getInt("id"));
        item.setProductId(rs.getInt("product_id"));
        item.setProductNameSnapshot(rs.getString("product_name_snapshot"));
        item.setUnitPrice(rs.getFloat("unit_price"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }

    private void loadItemsForOrder(Connection conn, Order order) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ITEMS_BY_ORDER_ID)) {
            ps.setInt(1, order.getId());
            try (ResultSet rs = ps.executeQuery()) {
                List<OrderItem> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(mapRowItem(rs));
                }
                order.setItems(items);
            }
        }
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
        for (CartItem ci : cart.getItems()) {
            if (ci.getQuantity() <= 0) {
                continue;
            }

            Product p = products.findById(ci.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("product not found: " + ci.getProductId()));

            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductNameSnapshot(p.getName());
            oi.setUnitPrice(p.getPrice());
            oi.setQuantity(ci.getQuantity());
            items.add(oi);
        }

        cart.getItems().clear();
        carts.save(cart);

        if (items.isEmpty()) {
            throw new IllegalArgumentException("no valid items to order");
        }

        order.setItems(items);
        return orders.save(order);
    }

    public void updateStatus(Integer orderId, String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("status is blank");
        }

        Order o = orders.findById(orderId).orElseThrow(() -> new NoSuchElementException("order not found"));
        o.setStatus(status);
        orders.update(o);
    }

    public List<Order> selectOrdersForPage(int offset, int limit) {
        return orders.findForPage(offset, limit);
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
