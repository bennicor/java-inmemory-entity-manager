package org.example.shop.infrastructure.repository.db.pg;

import org.example.shop.domain.model.Order;
import org.example.shop.domain.model.OrderItem;
import org.example.shop.domain.repository.OrderRepository;
import org.example.shop.infrastructure.repository.db.Db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PgOrderRepository extends PgGenericRepository<Order>
        implements OrderRepository {
    private static final String SQL_INSERT_ORDER = """
        insert into "order"(customer_id, order_date, delivery_cost, payment_method, status)
        values (?,?,?,?,?)
        returning id
        """;

    private static final String SQL_UPDATE_ORDER = """
        update "order"
        set customer_id = ?, order_date = ?, delivery_cost = ?, payment_method = ?, status = ?
        where id = ?
        """;

    private static final String SQL_SELECT_ORDER_BY_ID = """
        select id, customer_id, order_date, delivery_cost, payment_method, status
        from "order"
        where id = ?
        """;

    private static final String SQL_SELECT_ORDERS_BY_CUSTOMER_ID = """
        select id, customer_id, order_date, delivery_cost, payment_method, status
        from "order"
        where customer_id = ?
        order by order_date desc, id desc
        """;

    private static final String SQL_SELECT_ALL_ORDERS = """
        select id, customer_id, order_date, delivery_cost, payment_method, status
        from "order"
        order by order_date desc, id desc
        """;

    private static final String SQL_SELECT_ITEMS_BY_ORDER_ID = """
        select id, product_id, product_name_snapshot, unit_price, quantity
        from order_item
        where order_id = ?
        """;

    private static final String SQL_INSERT_ITEM = """
        insert into order_item(order_id, product_id, product_name_snapshot, unit_price, quantity)
        values (?,?,?,?,?)
        returning id
        """;

    @Override
    protected String getTableName() {
        return "\"order\"";
    }

    @Override
    protected Order mapRow(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setCustomerId(rs.getInt("customer_id"));

        Timestamp ts = rs.getTimestamp("order_date");
        o.setOrderDate(ts != null ? ts.toLocalDateTime() : null);

        o.setDeliveryCost(rs.getFloat("delivery_cost"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setStatus(rs.getString("status"));
        o.setItems(new ArrayList<>());
        return o;
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

    private void loadItemsForOrder(Connection conn, Order entity) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ITEMS_BY_ORDER_ID)) {
            ps.setInt(1, entity.getId());
            try (ResultSet rs = ps.executeQuery()) {
                List<OrderItem> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(mapRowItem(rs));
                }
                entity.setItems(items);
            }
        }
    }

    @Override
    public Order save(Order entity) {
        try (Connection conn = Db.getConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try {
                insert(conn, entity);

                insertItems(conn, entity);

                conn.commit();
                conn.setAutoCommit(oldAutoCommit);
                return entity;
            } catch (SQLException e) {
                conn.rollback();
                conn.setAutoCommit(oldAutoCommit);
                throw new RuntimeException("Error saving order", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving order (connection)", e);
        }
    }

    private void insert(Connection conn, Order entity) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_ORDER)) {
            ps.setInt(1, entity.getCustomerId());

            LocalDateTime dt = entity.getOrderDate();
            ps.setTimestamp(2, dt != null ? Timestamp.valueOf(dt) : Timestamp.from(java.time.Instant.now()));

            ps.setFloat(3, entity.getDeliveryCost());
            ps.setString(4, entity.getPaymentMethod());
            ps.setString(5, entity.getStatus());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Inserting order failed, no ID returned");
                }
            }
        }
    }

    @Override
    public Order update(Order entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity ID must not be null for update operation.");
        }

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ORDER)) {
            ps.setInt(1, entity.getCustomerId());

            LocalDateTime dt = entity.getOrderDate();
            ps.setTimestamp(2, dt != null ? Timestamp.valueOf(dt) : null);

            ps.setFloat(3, entity.getDeliveryCost());
            ps.setString(4, entity.getPaymentMethod());
            ps.setString(5, entity.getStatus());
            ps.setInt(6, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Database error during Order update operation.", e);
        }
    }

    private void insertItems(Connection conn, Order entity) throws SQLException {
        List<OrderItem> items = entity.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_ITEM)) {
            for (OrderItem item : items) {
                ps.setInt(1, entity.getId());
                ps.setInt(2, item.getProductId());
                ps.setString(3, item.getProductNameSnapshot());
                ps.setFloat(4, item.getUnitPrice());
                ps.setInt(5, item.getQuantity());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        item.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public Optional<Order> findById(Integer id) {
        try (Connection conn = Db.getConnection()) {
            Order order;
            try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return Optional.empty();
                    }
                    order = mapRow(rs);
                }
            }

            loadItemsForOrder(conn, order);
            return Optional.of(order);
        } catch (SQLException e) {
            throw new RuntimeException("Error findById order", e);
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> result = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL_ORDERS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = mapRow(rs);
                loadItemsForOrder(conn, order);
                result.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error findAll orders", e);
        }
        return result;
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        List<Order> result = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ORDERS_BY_CUSTOMER_ID)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapRow(rs);
                    loadItemsForOrder(conn, order);
                    result.add(order);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error findByCustomerId orders", e);
        }
        return result;
    }
}