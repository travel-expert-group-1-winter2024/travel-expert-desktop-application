package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.ProductDAO;
import org.example.travelexpertdesktopapplication.models.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductController {
    @FXML private TextField txtSearch;
    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;

    @FXML private JFXButton btnAdd, btnDelete, btnUpdate, btnReset;
    @FXML
    private ImageView btnSearch;

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

    /**  Load all products from the database */
    @FXML
    public void loadProducts() {
        productList.setAll(ProductDAO.getAllProducts());
        tableProducts.setItems(productList);
    }

    /**  Search for products dynamically */
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

    /**  Reset the search field and reload products */
    @FXML
    private void resetSearch() {
        txtSearch.clear();
        loadProducts();
    }

    /**  Open the product form to add a new product */
    @FXML
    private void onAddProduct() {
        openProductForm(null);
    }

    /**  Open the product form to update a selected product */
    @FXML
    private void onUpdateProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            openProductForm(selected);
        } else {
            showAlert("No Selection", "Please select a product to update.");
        }
    }

    /**  Delete a selected product with confirmation */
    @FXML
    private void onDeleteProduct() {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Confirm before deleting
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this product: " + selected.getProductName() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean success = ProductDAO.deleteProduct(selected.getProductId());
                if (success) {
                    loadProducts();
                    showSuccess("Success", "Product deleted successfully.");
                } else {
                    showAlert("Error", "Failed to delete product.");
                }
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

            // Show and wait until the form is closed
            stage.showAndWait();

            //  Check if the product was actually added/updated before showing a success message
            if (controller.isProductSaved()) {
                if (product == null) {
                    showSuccess("Success", "Product added successfully.");
                } else {
                    showSuccess("Success", "Product updated successfully.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**  Show a success message */
    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**  Show an error or warning message */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
