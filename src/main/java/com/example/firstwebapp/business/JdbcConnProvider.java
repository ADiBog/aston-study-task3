package com.example.firstwebapp.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnProvider implements ConnectionProvider {
    private static final JdbcConnProvider jdbcConnectionProvider = new JdbcConnProvider();
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/oDBastard";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";

    private JdbcConnProvider() {
    }

    public static JdbcConnProvider getJdbcConnectionProvider() {
        return jdbcConnectionProvider;
    }

    public Connection getNewConnection() {
        try {
            Class.forName(DB_DRIVER);
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Не удалось создать подключение");
        }
    }
}
