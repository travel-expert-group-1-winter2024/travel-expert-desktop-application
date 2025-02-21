package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.models.Product;
import org.example.travelexpertdesktopapplication.utils.DbConfig;
import org.tinylog.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String SELECT_ALL = "SELECT * FROM public.products ORDER BY productid ASC";
    private static final String INSERT = "INSERT INTO public.products (productid, prodname) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE public.products SET prodname = ? WHERE productid = ?";
    private static final String DELETE = "DELETE FROM public.products WHERE productid = ?";
    private static final String GET_NEXT_ID = "SELECT MAX(productid) FROM public.products";

    /**
     * Get all products from the database
     */
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        Logger.debug("Fetching all products from the database.");

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL)) {

            Logger.debug("Executing SQL query: {}", SELECT_ALL);
            while (rs.next()) {
                int id = rs.getInt("productid");
                String name = rs.getString("prodname");
                products.add(new Product(id, name));
            }
            Logger.info("Retrieved {} products.", products.size());
        } catch (SQLException e) {
            Logger.error(e, "Error fetching products from the database.");
        }
        return products;
    }

    /**
     * Get the next available product ID
     */
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
            Logger.error(e, "Error retrieving next product ID.");
        }
        return nextId;
    }

    /**
     * Add a new product
     */
    public static boolean addProduct(String name) {
        boolean isInserted = false;
        int newId = getNextProductId(); // Get next available ID

        Logger.debug("Adding new product: {}", name);
        Logger.debug("Generated Product ID: {}", newId);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
            Logger.debug("Executing SQL query: {}", INSERT);
            pstmt.setInt(1, newId);
            pstmt.setString(2, name);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                Logger.info("Product added successfully. ID={}, Name={}", newId, name);
                isInserted = true;
            } else {
                Logger.warn("Failed to add product: {}", name);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error adding product: {}", name);
        }
        return isInserted;
    }

    /**
     * Update a product
     */
    public static boolean updateProduct(int id, String name) {
        boolean isUpdated = false;
        Logger.debug("Updating product. ID={}, New Name={}", id, name);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {
            Logger.debug("Executing SQL query: {}", UPDATE);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                Logger.info("Product updated successfully. ID={}, New Name={}", id, name);
                isUpdated = true;
            } else {
                Logger.warn("No product updated. Possibly invalid ID: {}", id);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error updating product. ID={}, New Name={}", id, name);
        }
        return isUpdated;
    }

    /**
     * Delete a product
     */
    public static boolean deleteProduct(int id) {
        boolean isDeleted = false;
        Logger.debug("Deleting product. ID={}", id);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE)) {
            Logger.debug("Executing SQL query: {}", DELETE);
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                Logger.info("Product deleted successfully. ID={}", id);
                isDeleted = true;
            } else {
                Logger.warn("No product deleted. Possibly invalid ID: {}", id);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error deleting product. ID={}", id);
        }
        return isDeleted;
    }
}
