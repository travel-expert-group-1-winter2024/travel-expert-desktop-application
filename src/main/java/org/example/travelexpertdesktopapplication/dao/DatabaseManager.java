package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.utils.DbConfig;
import org.tinylog.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = DbConfig.getProperty("url");
    private static final String USER = DbConfig.getProperty("user");
    private static final String PASSWORD = DbConfig.getProperty("password");

    public static Connection getConnection() {
        Logger.debug("Attempting to establish a database connection...");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null && !connection.isClosed()) {
                Logger.debug("Database connection established.");
                return connection;
            }
        } catch (SQLException e) {
            Logger.error(e, "Error establishing a database connection.");
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

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Logger.error(e, "Error closing database connection.");
            }
        }
    }
}