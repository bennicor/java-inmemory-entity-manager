package org.example.shop.infrastructure.repository.db.pg;

import org.example.shop.domain.model.Cart;
import org.example.shop.domain.model.CartItem;
import org.example.shop.domain.repository.CartRepository;
import org.example.shop.infrastructure.repository.db.Db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PgCartRepository extends PgGenericRepository<Cart>
        implements CartRepository {
    private static final String SQL_INSERT_CART =
            "insert into cart(customer_id) values (?) returning id";

    private static final String SQL_SELECT_BY_ID =
            "select id, customer_id from cart where id=?";

    private static final String SQL_SELECT_ALL =
            "select id, customer_id from cart order by id";

    private static final String SQL_UPDATE_CART =
            "update cart set customer_id=? where id=?";

    private static final String SQL_SELECT_BY_CUSTOMER_ID =
            "select id, customer_id from cart where customer_id=?";

    private static final String SQL_SELECT_ITEMS_BY_CART_ID =
            "select id, product_id, quantity from cart_item where cart_id=?";

    private static final String SQL_INSERT_ITEM =
            "insert into cart_item(cart_id, product_id, quantity) values (?,?,?) returning id";

    private static final String SQL_DELETE_ITEMS_BY_CART_ID =
            "delete from cart_item where cart_id=?";

    @Override
    protected String getTableName() {
        return "cart";
    }

    @Override
    protected Cart mapRow(ResultSet rs) throws SQLException {
        Cart c = new Cart();
        c.setId(rs.getInt("id"));
        c.setCustomerId(rs.getInt("customer_id"));
        c.setItems(new ArrayList<>());
        return c;
    }

    private CartItem mapRowItem(ResultSet rs) throws SQLException {
        CartItem item = new CartItem();
        item.setId(rs.getInt("id"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }

    private void loadItemsForCart(Connection conn, Cart cart) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ITEMS_BY_CART_ID)) {
            ps.setInt(1, cart.getId());
            try (ResultSet rs = ps.executeQuery()) {
                List<CartItem> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(mapRowItem(rs));
                }
                cart.setItems(items);
            }
        }
    }

    @Override
    public Cart save(Cart entity) {
        try (Connection conn = Db.getConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try {
                if (entity.getId() == null) {
                    insert(conn, entity);
                }

                deleteItemsByCartId(conn, entity.getId());
                insertItems(conn, entity);

                conn.commit();
                conn.setAutoCommit(oldAutoCommit);
                return entity;
            } catch (SQLException e) {
                conn.rollback();
                conn.setAutoCommit(oldAutoCommit);
                throw new RuntimeException("Error saving cart", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving cart (connection)", e);
        }
    }

    private void insert(Connection conn, Cart entity) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT_CART, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, entity.getCustomerId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving failed, no rows affected.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                } else {
                    throw new SQLException("Inserting cart failed, no ID returned");
                }
            }
        }
    }

    @Override
    public Cart update(Cart entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity ID must not be null for update operation.");
        }

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_CART)) {
            ps.setInt(1, entity.getCustomerId());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Database error during Cart update operation.", e);
        }
    }

    private void deleteItemsByCartId(Connection conn, Integer cartId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_ITEMS_BY_CART_ID)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        }
    }

    private void insertItems(Connection conn, Cart entity) throws SQLException {
        if (entity.getItems() == null || entity.getItems().isEmpty()) {
            return;
        }

        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_ITEM)) {
            for (CartItem item : entity.getItems()) {
                ps.setInt(1, entity.getId());
                ps.setInt(2, item.getProductId());
                ps.setInt(3, item.getQuantity());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        item.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public Optional<Cart> findById(Integer id) {
        try (Connection conn = Db.getConnection()) {
            Cart cart;
            try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return Optional.empty();
                    }
                    cart = mapRow(rs);
                }
            }

            loadItemsForCart(conn, cart);
            return Optional.of(cart);
        } catch (SQLException e) {
            throw new RuntimeException("Error findById cart", e);
        }
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> result = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cart cart = mapRow(rs);
                loadItemsForCart(conn, cart);
                result.add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error findAll carts", e);
        }
        return result;
    }

    @Override
    public Optional<Cart> findByCustomerId(Integer customerId) {
        try (Connection conn = Db.getConnection()) {
            Cart cart;
            try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_CUSTOMER_ID)) {
                ps.setInt(1, customerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return Optional.empty();
                    }
                    cart = mapRow(rs);
                }
            }

            loadItemsForCart(conn, cart);
            return Optional.of(cart);
        } catch (SQLException e) {
            throw new RuntimeException("Error findByCustomerId cart", e);
        }
    }
}
