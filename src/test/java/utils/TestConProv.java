package utils;

import com.example.firstwebapp.business.ConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConProv implements ConnectionProvider {
    private static final String H2_CONNECTION_STRING = "jdbc:h2:mem:test";
    @Override
    public Connection getNewConnection() {
        try {
            return DriverManager.getConnection(H2_CONNECTION_STRING);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
