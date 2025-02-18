package org.example.travelexpertdesktopapplication.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.travelexpertdesktopapplication.dao.ProductDAO;
import org.example.travelexpertdesktopapplication.models.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    @FXML private TextField txtSearch;
    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private Button btnSearch, btnReset;
    @FXML private Button btnAdd, btnUpdate, btnDelete, btnLoad;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup table columns
        productIdColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        productNameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));

        // Load products initially
        loadProducts();

        // Dynamic Search - Listen to search input
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            final String searchText = newValue.trim().toLowerCase(); // Ensure final variable
            searchProducts(searchText);
        });

        // Set up button actions
        btnSearch.setOnAction(event -> searchProducts(txtSearch.getText().trim()));
        btnReset.setOnAction(event -> resetSearch());
    }

    @FXML
    private void loadProducts() {
        productList.setAll(ProductDAO.getAllProducts());
        tableProducts.setItems(productList);
    }

    @FXML
    private void searchProducts(final String searchText) {
        if (searchText.isEmpty()) {
            loadProducts(); // If search field is empty, reload all products
            return;
        }

        List<Product> filteredProducts = productList.stream()
                .filter(product -> String.valueOf(product.getProductId()).contains(searchText) ||
                        product.getProductName().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        tableProducts.setItems(FXCollections.observableArrayList(filteredProducts));
    }

    @FXML
    private void resetSearch() {
        txtSearch.clear(); // Clear the search field
        loadProducts();    // Reload all products
    }
}
