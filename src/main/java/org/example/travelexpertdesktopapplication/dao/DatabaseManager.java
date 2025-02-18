package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.utils.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = DbConfig.getProperty("url");
    private static final String USER = DbConfig.getProperty("user");
    private static final String PASSWORD = DbConfig.getProperty("password");

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection successful!");
                return connection;
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}