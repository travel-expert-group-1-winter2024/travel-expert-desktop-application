package org.example.travelexpertdesktopapplication.services;

import org.example.travelexpertdesktopapplication.utils.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
//    private static final String URL = "jdbc:postgresql://localhost:5432/travelexperts";
//    private static final String USER = "postgres"; // Your PostgreSQL username
//    private static final String PASSWORD = ""; // Your PostgreSQL password

    private static final String URL = DbConfig.getProperty("url");
    private static final String USER = DbConfig.getProperty("user");
    private static final String PASSWORD = DbConfig.getProperty("password");

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

