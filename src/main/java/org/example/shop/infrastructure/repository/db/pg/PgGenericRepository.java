package org.example.shop.infrastructure.repository.db.pg;

import org.example.shop.domain.repository.CrudRepository;
import org.example.shop.domain.repository.MutableIdentifiable;
import org.example.shop.infrastructure.repository.db.Db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class PgGenericRepository<T extends MutableIdentifiable<Integer>>
        implements CrudRepository<T, Integer> {

    protected abstract String getTableName();

    protected abstract T mapRow(ResultSet rs) throws SQLException;

    @Override
    public abstract T save(T entity);

    @Override
    public Optional<T> findById(Integer id) {
        String sql = "select * from " + getTableName() +
                " where id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during findById in " + getTableName(), e);
        }
    }

    @Override
    public List<T> findAll() {
        String sql = "select * from " + getTableName() +
                " order by id";

        List<T> result = new ArrayList<>();

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during findAll in " + getTableName(), e);
        }

        return result;
    }

    @Override
    public boolean exists(Integer id) {
        String sql = "select 1 from " + getTableName() +
                " where id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during exists in " + getTableName(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "delete from " + getTableName() +
                " where id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error during deleteById in " + getTableName(), e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from " + getTableName();

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error during deleteAll in " + getTableName(), e);
        }
    }

    @Override
    public int count() {
        String sql = "select count(*) from " + getTableName();

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Database error during count in " + getTableName(), e);
        }
    }
}
