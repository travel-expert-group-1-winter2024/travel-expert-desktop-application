
package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.PackagesProductsSuppliersDAO;
import org.example.travelexpertdesktopapplication.dao.ProductDAO;
import org.example.travelexpertdesktopapplication.dao.ProductSupplierDAO;
import org.example.travelexpertdesktopapplication.dao.SupplierDAO;
import org.example.travelexpertdesktopapplication.models.PackagesProductsSuppliers;
import org.example.travelexpertdesktopapplication.models.Product;
import org.example.travelexpertdesktopapplication.models.ProductsSuppliers;
import org.example.travelexpertdesktopapplication.models.Supplier;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.tinylog.Logger;

public class AddEditProductSupplierController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="cboProduct1"
    private ComboBox<Product> cboProduct1; // Value injected by FXMLLoader

    @FXML // fx:id="cboProduct2"
    private ComboBox<Product> cboProduct2; // Value injected by FXMLLoader

    @FXML // fx:id="cboProduct3"
    private ComboBox<Product> cboProduct3; // Value injected by FXMLLoader

    @FXML // fx:id="cboSupplier1"
    private ComboBox<Supplier> cboSupplier1; // Value injected by FXMLLoader

    @FXML // fx:id="cboSupplier2"
    private ComboBox<Supplier> cboSupplier2; // Value injected by FXMLLoader

    @FXML // fx:id="cboSupplier3"
    private ComboBox<Supplier> cboSupplier3; // Value injected by FXMLLoader

    private int currentPackageId;
    private static ProductsSuppliers[] previousProductSupplierList = new ProductsSuppliers[3]; // fixed length
    private static ProductsSuppliers[] selectedProductSupplierList = new ProductsSuppliers[3]; // fixed length

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";
        assert cboProduct1 != null : "fx:id=\"cboProduct1\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";
        assert cboProduct2 != null : "fx:id=\"cboProduct2\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";
        assert cboProduct3 != null : "fx:id=\"cboProduct3\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";
        assert cboSupplier1 != null : "fx:id=\"cboSupplier1\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";
        assert cboSupplier2 != null : "fx:id=\"cboSupplier2\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";
        assert cboSupplier3 != null : "fx:id=\"cboSupplier3\" was not injected: check your FXML file 'add-edit-products-suppliers-view.fxml'.";

        loadAllProductAndSupplier();
        if (selectedProductSupplierList != null) {
            if (selectedProductSupplierList[0] != null || selectedProductSupplierList[1] != null || selectedProductSupplierList[2] != null) {
                loadProductsAndSuppliersFromList(selectedProductSupplierList);
            }
        }

    }

    public void setCurrentPackageId(int currentPackageId) {
        this.currentPackageId = currentPackageId;
    }

    private void loadAllProductAndSupplier() {
        // get product and supplier data from database
        List<Product> productList = ProductDAO.getAllProducts();
        cboProduct1.setItems(FXCollections.observableArrayList(productList));
        cboProduct2.setItems(FXCollections.observableArrayList(productList));
        cboProduct3.setItems(FXCollections.observableArrayList(productList));
        List<Supplier> supplierList = SupplierDAO.getAllSuppliers();
        cboSupplier1.setItems(FXCollections.observableArrayList(supplierList));
        cboSupplier2.setItems(FXCollections.observableArrayList(supplierList));
        cboSupplier3.setItems(FXCollections.observableArrayList(supplierList));
    }

    public void loadProductsAndSuppliersForEdit() {

        if (selectedProductSupplierList != null){
            if (selectedProductSupplierList[0] != null || selectedProductSupplierList[1] != null || selectedProductSupplierList[2] != null) {
                // set value to combo boxes
                Logger.info("Load Products and Suppliers from selectedProductSupplierList");
                loadProductsAndSuppliersFromList(selectedProductSupplierList);
                return;
            }
        }

        // find all products_suppliers id from package id
        List<PackagesProductsSuppliers> packagesProductsSuppliers = PackagesProductsSuppliersDAO.findPackagesProductsSuppliersByPackageId(currentPackageId);
        // get product and supplier data from packagesProductsSuppliers
        for (int i = 0; i < packagesProductsSuppliers.size(); i++) {
            if (i > 2) {
                break;
            }

            PackagesProductsSuppliers pps = packagesProductsSuppliers.get(i);

            ProductsSuppliers productsSuppliers = ProductSupplierDAO.getProductsSuppliersById(pps.getProductSupplierId());
            if (productsSuppliers == null) {
                Logger.error("ProductsSuppliers not found for ID: " + pps.getProductSupplierId());
                AlertBox.showAlert("Error", "ProductsSuppliers not found for ID: " + pps.getProductSupplierId(), Alert.AlertType.ERROR);
                return;
            }
            previousProductSupplierList[i] = productsSuppliers;

            Product product = ProductDAO.getProductById(productsSuppliers.getProductId());
            if (product == null) {
                Logger.error("Product not found for ID: " + productsSuppliers.getProductId() + " in products_suppliers id: " + productsSuppliers.getProductSupplierId());
                AlertBox.showAlert("Error", "Product not found for ID: " + productsSuppliers.getProductId(), Alert.AlertType.ERROR);
                return;
            }
            Supplier supplier = SupplierDAO.getSupplierById(productsSuppliers.getSupplierId());
            if (supplier == null) {
                Logger.error("Supplier not found for ID: " + productsSuppliers.getSupplierId());
                AlertBox.showAlert("Error", "Supplier not found for ID: " + productsSuppliers.getSupplierId(), Alert.AlertType.ERROR);
                return;
            }

            // set the product and supplier to the combo boxes
            if (i == 0) {
                cboProduct1.setValue(product);
                cboSupplier1.setValue(supplier);
            } else if (i == 1) {
                cboProduct2.setValue(product);
                cboSupplier2.setValue(supplier);
            } else {
                cboProduct3.setValue(product);
                cboSupplier3.setValue(supplier);
            }
        }
    }

    private void loadProductsAndSuppliersFromList(ProductsSuppliers[] productsSuppliers) {
        for (int i = 0; i < productsSuppliers.length; i++) {
            if (i == 0 && productsSuppliers[i] != null) {
                cboProduct1.setValue(productsSuppliers[i].getProduct());
                cboSupplier1.setValue(productsSuppliers[i].getSupplier());
            } else if (i == 1 && productsSuppliers[i] != null) {
                cboProduct2.setValue(productsSuppliers[i].getProduct());
                cboSupplier2.setValue(productsSuppliers[i].getSupplier());
            } else if (i == 2 && productsSuppliers[i] != null) {
                cboProduct3.setValue(productsSuppliers[i].getProduct());
                cboSupplier3.setValue(productsSuppliers[i].getSupplier());
            }
        }
    }

    @FXML
    void onSaveClicked(MouseEvent event) {
        // find product_supplier id from product id and supplier id
        if (cboProduct1.getValue() != null || cboSupplier1.getValue() != null) {
            addProductSupplierToList(0, cboProduct1, cboSupplier1);
        }
        if (cboProduct2.getValue() != null || cboSupplier2.getValue() != null) {
            addProductSupplierToList(1, cboProduct2, cboSupplier2);
        }
        if (cboProduct3.getValue() != null || cboSupplier3.getValue() != null) {
            addProductSupplierToList(2, cboProduct3, cboSupplier3);
        }

        // close
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    private void addProductSupplierToList(int index, ComboBox<Product> cboProduct, ComboBox<Supplier> cboSupplier) {
        Product selectedProduct = cboProduct.getSelectionModel().getSelectedItem();
        Supplier selectedSupplier = cboSupplier.getSelectionModel().getSelectedItem();
        ProductsSuppliers productsSuppliers = new ProductsSuppliers(
                selectedProduct.getProductId(),
                selectedSupplier.getSupplierid()
        );

        productsSuppliers.setProduct(selectedProduct);
        productsSuppliers.setSupplier(selectedSupplier);
        selectedProductSupplierList[index] = productsSuppliers;
    }

    @FXML
    void onCancelClicked(MouseEvent event) {
        // close this window
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public static ProductsSuppliers[] getSelectedProductSupplierList() {
        return selectedProductSupplierList;
    }

    public static void clearSelectedProductSuppliers() {
        selectedProductSupplierList = new ProductsSuppliers[3]; // fixed length
    }

    public static ProductsSuppliers[] getPreviousProductSupplierList() {
        return previousProductSupplierList;
    }

    public static void clearPreviousProductSupplierIds() {
        previousProductSupplierList = new ProductsSuppliers[3]; // fixed length
    }

}
