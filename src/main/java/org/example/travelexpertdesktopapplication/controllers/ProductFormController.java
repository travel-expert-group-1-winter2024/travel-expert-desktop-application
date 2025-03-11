package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.ProductDAO;
import org.example.travelexpertdesktopapplication.models.Product;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Validator;

import static org.example.travelexpertdesktopapplication.utils.ValidateFields.validateField;

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
        txtProductName.textProperty().addListener((obs, oldVal, newVal) -> isFormValid());
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
        if (isFormValid()) {
            if (selectedProduct == null) {
                boolean success = ProductDAO.addProduct(txtProductName.getText().trim());
                if (success) {
                    productController.loadProducts();
                    productSaved = true; //
                    closeWindow();
                } else {
                    AlertBox.showAlert("Error", "Failed to add product.", Alert.AlertType.ERROR);
                }
            } else {
                int productId = selectedProduct.getProductId();
                boolean success = ProductDAO.updateProduct(productId, txtProductName.getText().trim());
                if (success) {
                    productController.loadProducts();
                    productSaved = true;
                    closeWindow();
                } else {
                    AlertBox.showAlert("Error", "Failed to Update product.", Alert.AlertType.ERROR);
                }
            }
        }
    }

    /** Handle cancel button */
    @FXML
    private void onCancel() {
        productSaved = false;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private boolean isFormValid(){
        boolean isValid = true;
        isValid &= validateField(txtProductName, Validator.checkForEmpty(txtProductName.getText().trim(),"Product"));
        return isValid;
    }

    public boolean isProductSaved() {
        return productSaved;
    }
}
