package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.ProductDAO;
import org.example.travelexpertdesktopapplication.models.Product;

public class ProductFormController {

    @FXML private TextField txtProductId;
    @FXML private TextField txtProductName;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private ProductController productController;
    private Product selectedProduct;

    public void setProductController(ProductController controller) {
        this.productController = controller;
    }

    public void setProductData(Product product) {
        if (product != null) {
            selectedProduct = product;
            txtProductId.setText(String.valueOf(product.getProductId()));
            txtProductId.setDisable(true); // Prevent changing ID on update
            txtProductName.setText(product.getProductName());
        }
    }

    @FXML
    private void onSave() {
        System.out.println("Save Product Clicked");
        String idText = txtProductId.getText();
        String name = txtProductName.getText().trim();

        if (idText.isEmpty() || name.isEmpty()) {
            showAlert("Validation Error", "Product ID and Name cannot be empty.");
            return;
        }

        int productId = Integer.parseInt(idText);

        if (selectedProduct == null) {
            // Adding a new product
            boolean success = ProductDAO.addProduct(productId, name);
            if (success) {
                System.out.println("Product Added Successfully");
                productController.loadProducts();
                closeWindow();
            } else {
                showAlert("Error", "Failed to add product.");
            }
        } else {
            // Updating existing product
            boolean success = ProductDAO.updateProduct(productId, name);
            if (success) {
                System.out.println("Product Updated Successfully");
                productController.loadProducts();
                closeWindow();
            } else {
                showAlert("Error", "Failed to update product.");
            }
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
