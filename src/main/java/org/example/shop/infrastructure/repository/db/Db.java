package org.example.shop.infrastructure.repository.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Db {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        URL = System.getenv("DB_URL");
        USER = System.getenv("DB_USER");
        PASSWORD = System.getenv("DB_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found. Add dependency org.postgresql:postgresql", e);
        }
    }

    private Db() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed DB connection " + e, e);
        }
    }
}

