package org.example.travelexpertdesktopapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database URL (Change "mydatabase" to your actual database name)
    private static final String URL = "jdbc:postgresql://localhost:5432/travelexperts";
    private static final String USER = "postgres"; // Your PostgreSQL username
    private static final String PASSWORD = "Siddharth@04"; // Your PostgreSQL password

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        return connection;
    }
}

