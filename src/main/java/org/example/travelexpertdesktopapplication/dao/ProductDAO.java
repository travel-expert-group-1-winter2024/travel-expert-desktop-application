package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.models.Product;
import org.example.travelexpertdesktopapplication.utils.DbConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String SELECT_ALL = "SELECT * FROM public.products ORDER BY productid ASC";
    private static final String INSERT = "INSERT INTO public.products (productid, prodname) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE public.products SET prodname = ? WHERE productid = ?";
    private static final String DELETE = "DELETE FROM public.products WHERE productid = ?";

    // Method to create a database connection using DbConfig properties
    private static Connection getConnection() throws SQLException {
        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        return DriverManager.getConnection(url, user, password);
    }

    // Method to get all products from the database
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                int id = rs.getInt("productid");
                String name = rs.getString("prodname");
                products.add(new Product(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Method to add a new product
    public static boolean addProduct(int id, String name) {
        boolean isInserted = false;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            isInserted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInserted;
    }

    // Method to update a product
    public static boolean updateProduct(int id, String name) {
        boolean isUpdated = false;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            isUpdated = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    // Method to delete a product
    public static boolean deleteProduct(int id) {
        boolean isDeleted = false;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE)) {

            pstmt.setInt(1, id);
            isDeleted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}
