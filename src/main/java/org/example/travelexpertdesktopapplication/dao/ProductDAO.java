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
    private static final String GET_NEXT_ID = "SELECT MAX(productid) FROM public.products";


    /** Get all products from the database */
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
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

    /** Get the next available product ID */
    public static int getNextProductId() {
        int nextId = 1; // Default to 1 if there are no products
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_NEXT_ID)) {

            if (rs.next()) {
                int lastId = rs.getInt(1);
                nextId = lastId + 1; // Increment by 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    /** Add a new product */
    public static boolean addProduct(String name) {
        boolean isInserted = false;
        int newId = getNextProductId(); // Get next available ID

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {

            pstmt.setInt(1, newId);
            pstmt.setString(2, name);
            isInserted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInserted;
    }

    /**  Update a product */
    public static boolean updateProduct(int id, String name) {
        boolean isUpdated = false;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            isUpdated = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    /**  Delete a product */
    public static boolean deleteProduct(int id) {
        boolean isDeleted = false;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE)) {

            pstmt.setInt(1, id);
            isDeleted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}
