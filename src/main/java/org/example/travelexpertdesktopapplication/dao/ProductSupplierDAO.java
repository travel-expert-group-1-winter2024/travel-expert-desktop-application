package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.models.Product;
import org.example.travelexpertdesktopapplication.models.ProductsSuppliers;
import org.tinylog.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductSupplierDAO {
    public static ProductsSuppliers getProductSupplierIdByProductIdAndSupplierId(int productId, int supplierId) {
        String query = "SELECT * FROM products_suppliers WHERE productid = ? AND supplierid = ? LIMIT 1";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, supplierId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Logger.debug("Executing SQL query: {}", query);

            if (resultSet.next()) {
                return new ProductsSuppliers(
                        resultSet.getInt("productid"),
                        resultSet.getInt("supplierid"),
                        resultSet.getInt("productsupplierid")
                );
            }
        } catch (SQLException e) {
            Logger.error(e, "Error fetching product supplier ID from the database.");
        }
        return null;
    }

    public static int addProductSupplier(ProductsSuppliers productSupplier) {
        int rowsAffected = 0;
        String query = "INSERT INTO products_suppliers (productid, supplierid) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productSupplier.getProductId());
            preparedStatement.setInt(2, productSupplier.getSupplierId());
            rowsAffected = preparedStatement.executeUpdate();
            Logger.debug("Executing SQL query: {}", query);
        } catch (SQLException e) {
            Logger.error(e, "Error adding product supplier to the database.");
        }
        return rowsAffected;
    }
}
