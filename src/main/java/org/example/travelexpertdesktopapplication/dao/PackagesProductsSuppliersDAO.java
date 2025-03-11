package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.models.PackagesProductsSuppliers;
import org.tinylog.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagesProductsSuppliersDAO {

    public static List<PackagesProductsSuppliers> findPackagesProductsSuppliersByPackageId(int packageId) {
        List<PackagesProductsSuppliers> packagesProductsSuppliersList = new ArrayList<>();
        String sql = "SELECT packageid, productsupplierid FROM packages_products_suppliers WHERE packageid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, packageId);
            ResultSet rs = stmt.executeQuery();

            Logger.debug("Executing query: {}", sql);

            while (rs.next()) {
                packagesProductsSuppliersList.add(new PackagesProductsSuppliers(
                        rs.getInt("packageid"),
                        rs.getInt("productsupplierid")
                ));
            }
        } catch (SQLException e) {
            Logger.error(e, "Error fetching package-product-supplier mappings for package ID: {}", packageId);
        }
        return packagesProductsSuppliersList;
    }

    public static PackagesProductsSuppliers findPackagesProductsSuppliersById(int packageId, int productSupplierId) {
        PackagesProductsSuppliers packageProductSupplier = null;
        String sql = "SELECT packageid, productsupplierid FROM packages_products_suppliers WHERE packageid = ? AND productsupplierid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, packageId);
            stmt.setInt(2, productSupplierId);
            ResultSet rs = stmt.executeQuery();

            Logger.debug("Executing query: {}", sql);

            if (rs.next()) {
                packageProductSupplier = new PackagesProductsSuppliers(
                        rs.getInt("packageid"),
                        rs.getInt("productsupplierid")
                );
            }
        } catch (SQLException e) {
            Logger.error(e, "Error fetching package-product-supplier mapping for package ID: {} and product supplier ID: {}", packageId, productSupplierId);
        }
        return packageProductSupplier;
    }

    public static int addPackageProductSupplier(PackagesProductsSuppliers packageProductSupplier) {
        int rowsAffected = 0;
        String query = "INSERT INTO packages_products_suppliers (packageid, productsupplierid) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, packageProductSupplier.getPackageId());
            preparedStatement.setInt(2, packageProductSupplier.getProductSupplierId());
            rowsAffected = preparedStatement.executeUpdate();
            Logger.debug("Executing SQL query: {}", query);
        } catch (SQLException e) {
            Logger.error(e, "Error adding package product supplier to the database.");
        }
        return rowsAffected;
    }

    public static int updatePackageProductSupplier(PackagesProductsSuppliers packageProductSupplier) {
        int rowsAffected = 0;
        String query = "UPDATE packages_products_suppliers SET productsupplierid = ? WHERE packageid = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, packageProductSupplier.getProductSupplierId());
            preparedStatement.setInt(2, packageProductSupplier.getPackageId());
            rowsAffected = preparedStatement.executeUpdate();
            Logger.debug("Executing SQL query: {}", query);
        } catch (SQLException e) {
            Logger.error(e, "Error updating package product supplier in the database.");
        }
        return rowsAffected;
    }

    public static void deletePackageProductSupplierByProductSupplierId(Integer id) {
        String sql = "DELETE FROM packages_products_suppliers WHERE productsupplierid = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            Logger.debug("Executing SQL query: {}", sql);
            if (rowsAffected > 0) {
                Logger.info("Deleted package product supplier with ID: {}", id);
            } else {
                Logger.warn("No package product supplier found with ID: {}", id);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error deleting package product supplier with ID: {}", id);
        }
    }

    public static void deletePackageProductSupplierByPackageId(Integer id) throws SQLException {
        String sql = "DELETE FROM packages_products_suppliers WHERE packageid = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            Logger.debug("Executing SQL query: {}", sql);
            if (rowsAffected > 0) {
                Logger.info("Deleted package product supplier with ID: {}", id);
            } else {
                Logger.warn("No package product supplier found with ID: {}", id);
            }
        } catch (SQLException e) {
            Logger.error(e, "Error deleting package product supplier with ID: {}", id);
            throw new SQLException(e);
        }
    }
}
