package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.utils.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class DatabaseManager {
    private static final int POOL_SIZE = 5;  // max pool
    private static final Queue<Connection> connectionPool = new LinkedList<>();

    static {
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                connectionPool.add(createNewConnection());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing database connection pool", e);
        }
    }

    private static Connection createNewConnection() throws SQLException {
        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Get a database connection from the pool
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            return createNewConnection();
        }
        return connectionPool.poll();
    }

    /**
     * Return a connection back to the pool
     */
    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.offer(connection);
        }
    }
}