package org.example.shop.infrastructure.repository.db.pg;

import org.example.shop.domain.model.Customer;
import org.example.shop.domain.repository.CustomerRepository;
import org.example.shop.infrastructure.repository.db.Db;

import java.sql.*;
import java.util.Optional;

public class PgCustomerRepository extends PgGenericRepository<Customer>
        implements CustomerRepository {
    private static final String SQL_INSERT = """
        insert into customer(last_name, first_name, middle_name, address, phone, email)
        values (?,?,?,?,?,?)
        returning id
        """;

    private static final String SQL_UPDATE = """
        update customer
        set last_name=?, first_name=?, middle_name=?, address=?, phone=?, email=?
        where id=?
        """;

    private static final String SQL_FIND_BY_EMAIL = "select * from customer where email=?";

    private static final String SQL_FIND_BY_PHONE = "select * from customer where phone=?";

    private void setStatementParams(PreparedStatement ps, Customer entity) throws SQLException {
        int i = 1;
        ps.setString(i++, entity.getLastName());
        ps.setString(i++, entity.getFirstName());
        ps.setString(i++, entity.getMiddleName());
        ps.setString(i++, entity.getAddress());
        ps.setString(i++, entity.getPhone());
        ps.setString(i++, entity.getEmail());
    }

    @Override
    protected String getTableName() {
        return "customer";
    }

    @Override
    protected Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getInt("id"));
        c.setLastName(rs.getString("last_name"));
        c.setFirstName(rs.getString("first_name"));
        c.setMiddleName(rs.getString("middle_name"));
        c.setAddress(rs.getString("address"));
        c.setPhone(rs.getString("phone"));
        c.setEmail(rs.getString("email"));
        return c;
    }

    @Override
    public Customer save(Customer entity) {
        try (Connection conn = Db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setStatementParams(pstmt, entity);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving failed, no rows affected.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Integer id = rs.getInt(1);
                    entity.setId(id);
                    return entity;
                } else {
                    throw new SQLException("Saving failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during save operation.", e);
        }
    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_PHONE)) {

            ps.setString(1, phone);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error findByEmail", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_EMAIL)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error findByEmail", e);
        }
        return Optional.empty();
    }

    public Customer update(Customer entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity ID must not be null for update operation.");
        }

        try (Connection conn = Db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            setStatementParams(pstmt, entity);
            pstmt.setInt(7, entity.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Update failed, entity with ID " + entity.getId() + " not found.");
            }

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Database error during Ability update operation.", e);
        }
    }
}
