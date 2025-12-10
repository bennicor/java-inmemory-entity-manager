package org.example.shop.infrastructure.repository.db.pg;

import org.example.shop.domain.model.Product;
import org.example.shop.domain.repository.ProductRepository;
import org.example.shop.infrastructure.repository.db.Db;

import java.sql.*;
import java.util.Optional;


public class PgProductRepository extends PgGenericRepository<Product>
        implements ProductRepository {
    private static final String SQL_INSERT = """
        insert into product(code,name,price,weight_kg,length_cm,width_cm,height_cm,description)
        values (?,?,?,?,?,?,?,?)
        returning id
        """;

    private static final String SQL_UPDATE = """
        update product
        set code=?, name=?, price=?, weight_kg=?, length_cm=?, width_cm=?, height_cm=?, description=?
        where id=?
        """;

    private static final String SQL_FIND_BY_CODE =
            "select * from product where code=?";

    private void setStatementParams(PreparedStatement pstmt, Product entity) throws SQLException {
        int i = 1;
        pstmt.setString(i++, entity.getCode());
        pstmt.setString(i++, entity.getName());
        pstmt.setFloat(i++, entity.getPrice());
        pstmt.setFloat(i++, entity.getWeightKg());
        pstmt.setInt(i++, entity.getLengthCm());
        pstmt.setInt(i++, entity.getWidthCm());
        pstmt.setInt(i++, entity.getHeightCm());
        pstmt.setString(i++, entity.getDescription());
    }

    @Override
    protected String getTableName() {
        return "product";
    }

    @Override
    protected Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setCode(rs.getString("code"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getFloat("price"));
        p.setWeightKg(rs.getFloat("weight_kg"));
        p.setLengthCm(rs.getInt("length_cm"));
        p.setWidthCm(rs.getInt("width_cm"));
        p.setHeightCm(rs.getInt("height_cm"));
        p.setDescription(rs.getString("description"));
        return p;
    }

    @Override
    public Product save(Product entity) {
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
    public Optional<Product> findByCode(String code) {
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_FIND_BY_CODE)) {

            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error findByCode product", e);
        }
    }

    public Product update(Product entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity ID must not be null for update operation.");
        }

        try (Connection conn = Db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            setStatementParams(pstmt, entity);
            pstmt.setInt(9, entity.getId());

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
