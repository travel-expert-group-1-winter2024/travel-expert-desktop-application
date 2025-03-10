package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.models.PackagesProductsSuppliers;
import org.tinylog.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PackagesProductsSuppliersDAO {

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
}
