package org.example.travelexpertdesktopapplication.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.ProductDAO;
import org.example.travelexpertdesktopapplication.models.Product;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    @FXML private TextField txtSearch;
    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private Button btnAdd, btnUpdate, btnDelete;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productIdColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());

        productNameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));

        loadProducts();
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> searchProducts(newValue.trim().toLowerCase()));
    }

    @FXML
    public void loadProducts() {
        productList.setAll(ProductDAO.getAllProducts());
        tableProducts.setItems(productList);
    }

    @FXML
    private void searchProducts(final String searchText) {
        if (searchText.isEmpty()) {
            loadProducts();
            return;
        }

        List<Product> filteredProducts = productList.stream()
                .filter(product -> String.valueOf(product.getProductId()).contains(searchText) ||
                        product.getProductName().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        tableProducts.setItems(FXCollections.observableArrayList(filteredProducts));
    }

    @FXML
    private void onAddProduct() {
        openProductForm(null);
    }

    @FXML
    private void onUpdateProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            openProductForm(selected);
        } else {
            showAlert("No Selection", "Please select a product to update.");
        }
    }

    @FXML
    private void onDeleteProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean success = ProductDAO.deleteProduct(selected.getProductId());
            if (success) {
                loadProducts();
            } else {
                showAlert("Error", "Failed to delete product.");
            }
        } else {
            showAlert("No Selection", "Please select a product to delete.");
        }
    }

    private void openProductForm(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/product-form.fxml"));
            Scene scene = new Scene(loader.load());

            ProductFormController controller = loader.getController();
            controller.setProductController(this);

            if (product != null) {
                controller.setProductData(product);
            }

            Stage stage = new Stage();
            stage.setTitle(product == null ? "Add Product" : "Edit Product");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
