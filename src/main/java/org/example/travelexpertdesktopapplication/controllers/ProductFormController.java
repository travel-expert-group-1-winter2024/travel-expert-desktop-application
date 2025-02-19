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
    private boolean productSaved = false;

    public void setProductController(ProductController controller) {
        this.productController = controller;
    }

    public void setProductData(Product product) {
        txtProductId.setEditable(false);
        txtProductId.setDisable(true);

        if (product != null) {
            selectedProduct = product;
            txtProductId.setText(String.valueOf(product.getProductId()));
            txtProductName.setText(product.getProductName());
        } else {
            txtProductId.setText("Auto-Generated");
            txtProductName.clear();
        }
    }

    /** Save new or updated product */
    @FXML
    private void onSave() {
        System.out.println("Save Product Clicked");
        String name = txtProductName.getText().trim();

        if (name.isEmpty()) {
            showAlert("Validation Error", "Product Name cannot be empty.");
            return;
        }

        if (selectedProduct == null) {
            boolean success = ProductDAO.addProduct(name);
            if (success) {
                System.out.println("Product Added Successfully");
                productController.loadProducts();
                productSaved = true; //
                closeWindow();
            } else {
                showAlert("Error", "Failed to add product.");
            }
        } else {
            int productId = selectedProduct.getProductId();
            boolean success = ProductDAO.updateProduct(productId, name);
            if (success) {
                System.out.println("Product Updated Successfully");
                productController.loadProducts();
                productSaved = true;
                closeWindow();
            } else {
                showAlert("Error", "Failed to update product.");
            }
        }
    }

    /** Handle cancel button */
    @FXML
    private void onCancel() {
        System.out.println("Cancel button clicked.");
        productSaved = false;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public boolean isProductSaved() {
        return productSaved;
    }

    /** Show alert messages */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
