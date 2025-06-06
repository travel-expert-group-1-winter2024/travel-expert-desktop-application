package org.example.travelexpertdesktopapplication.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.PackagesDAO;
import org.example.travelexpertdesktopapplication.models.Packages;
import org.example.travelexpertdesktopapplication.TEDesktopApp;
import org.example.travelexpertdesktopapplication.dao.*;
import org.example.travelexpertdesktopapplication.models.*;
import org.example.travelexpertdesktopapplication.services.StorageService;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Validator;
import org.tinylog.Logger;

import static org.example.travelexpertdesktopapplication.utils.ValidateFields.validateField;

public class AddEditPackageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSavePackage;

    @FXML
    private Button btnUploadPhoto;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    private Label lblAddEditPackage;

    @FXML
    private TextField tfBasePrice;

    @FXML
    private TextField tfCommission;

    @FXML
    private TextArea tfDesc;

    @FXML
    private TextField tfPackageID;

    @FXML
    private TextField tfPackageName;

    @FXML
    private Button btnProductsSuppliersAddEdit;

    @FXML
    private TextField tfPhotoURL;

    @FXML
    private TextField tfDestination;

    @FXML
    private TextField tfTags;

    private String mode;
    private int currentPackageId;

    @FXML
    void initialize() {
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert btnSavePackage != null : "fx:id=\"btnSavePackage\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert btnUploadPhoto != null : "fx:id=\"btnUploadPhoto\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert dpEndDate != null : "fx:id=\"dpEndDate\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert dpStartDate != null : "fx:id=\"dpStartDate\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert lblAddEditPackage != null : "fx:id=\"lblAddEditPackage\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfBasePrice != null : "fx:id=\"tfBasePrice\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfCommission != null : "fx:id=\"tfCommission\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfDesc != null : "fx:id=\"tfDesc\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfPackageID != null : "fx:id=\"tfPackageID\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfPackageName != null : "fx:id=\"tfPackageName\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert btnProductsSuppliersAddEdit != null : "fx:id=\"btnProductsSuppliersAddEdit\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfPhotoURL != null : "fx:id=\"tfPhotoURL\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";

        tfPackageID.setDisable(true);

        tfPackageName.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfDesc.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfCommission.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfBasePrice.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        dpStartDate.valueProperty().addListener((obs, oldVal, newVal) -> validateForm());
        dpEndDate.valueProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfDestination.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
    }

    @FXML
    void onUploadPhotoClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(btnUploadPhoto.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // You can customize the blob name (e.g., UUID + file extension)
                String blobName = selectedFile.getName();
                StorageService storage = StorageService.getInstance();
                String blobURL = storage.uploadFile(blobName, selectedFile);

                tfPhotoURL.setText(blobURL);
            } catch (Exception e) {
                Logger.error(e, "Error uploading file");
                AlertBox.showAlert("Upload Failed", "Could not upload photo.", Alert.AlertType.ERROR);
            }
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
        //change header label to current mode
        lblAddEditPackage.setText(mode + " Package");
    }

    @FXML
    void onProductSupplierAddEditClick(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/add-edit-products-suppliers-view.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        AddEditProductSupplierController controller = fxmlLoader.getController();
        controller.setCurrentPackageId(currentPackageId);
        if (mode.equalsIgnoreCase("edit")) {
            controller.loadProductsAndSuppliersForEdit();
        }

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add/Edit Product Supplier");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onClickSave() {
        if (validateForm()) {
            Packages packagesData = getDetailsOfPackageFromForm();
                if (mode.equalsIgnoreCase("add")) {
                    try {
                        int packageId = PackagesDAO.addPackage(packagesData);
                        saveProductSupplier(packageId);
                        AlertBox.showAlert("Success", "Package has been saved successfully!", Alert.AlertType.INFORMATION);
                        this.onExit();
                    }
                    catch (SQLException e){
                        AlertBox.showAlert("Error","Error adding Package", Alert.AlertType.ERROR);
                    }
                } else {
                    try {
                        Integer packageID = Integer.parseInt(tfPackageID.getText());
                        packagesData.setPackageid(packageID);
                        PackagesDAO.updatePackage(packagesData);

                        updateProductSupplier(packageID);

                        AlertBox.showAlert("Success", "Package has been updated successfully!", Alert.AlertType.INFORMATION);
                        this.onExit();
                    }
                    catch (SQLException e){
                        AlertBox.showAlert("Error","Error Updating Package", Alert.AlertType.ERROR);
                    }
                }
        }

    }

    private Packages getDetailsOfPackageFromForm() {
        SimpleStringProperty pkgName = new SimpleStringProperty(tfPackageName.getText());
        SimpleObjectProperty<LocalDate> pkgstartdate = new SimpleObjectProperty(dpStartDate.getValue());
        SimpleObjectProperty<LocalDate> pkgenddate = new SimpleObjectProperty(dpEndDate.getValue());
        SimpleStringProperty pkgdesc = new SimpleStringProperty(tfDesc.getText());
        SimpleIntegerProperty pkgbaseprice = new SimpleIntegerProperty(Integer.parseInt(tfBasePrice.getText()));
        SimpleIntegerProperty pkgagencycommission = new SimpleIntegerProperty(Integer.parseInt(tfCommission.getText()));
        SimpleStringProperty photoPath = new SimpleStringProperty(tfPhotoURL.getText());
        SimpleStringProperty destination = new SimpleStringProperty(tfDestination.getText());
        SimpleStringProperty tags = new SimpleStringProperty(tfTags.getText());
        return new Packages(pkgName, pkgstartdate, pkgenddate, pkgdesc, pkgbaseprice, pkgagencycommission, photoPath,destination,tags);
    }

    private void saveProductSupplier(int packageId) {
        ProductsSuppliers[] selectedProductSuppliers = AddEditProductSupplierController.getSelectedProductSupplierList();
        for (ProductsSuppliers productSupplier : selectedProductSuppliers) {
            if (productSupplier == null) {
                continue;
            }
            int productSupplierId = createProductSupplierIfNotExist(productSupplier.getProductId(), productSupplier.getSupplierId());
            PackagesProductsSuppliersDAO.addPackageProductSupplier(new PackagesProductsSuppliers(packageId, productSupplierId));
        }
    }

    private void updateProductSupplier(int packageId) {
        ProductsSuppliers[] previousProductSupplierList = AddEditProductSupplierController.getPreviousProductSupplierList();
        ProductsSuppliers[] selectedProductSupplierList = AddEditProductSupplierController.getSelectedProductSupplierList();
        List<ProductsSuppliers> idsToAdd = compareAndUpdateProductSupplierIds(selectedProductSupplierList, previousProductSupplierList);

        // add new product suppliers ids
        for (ProductsSuppliers productSupplier : idsToAdd) {
            int productSupplierId = createProductSupplierIfNotExist(productSupplier.getProductId(), productSupplier.getSupplierId());
            PackagesProductsSuppliersDAO.addPackageProductSupplier(new PackagesProductsSuppliers(packageId, productSupplierId));
        }
    }

    private int createProductSupplierIfNotExist(int productId, int supplierId) {
        ProductsSuppliers productsSuppliers = ProductSupplierDAO.getProductSupplierIdByProductIdAndSupplierId(productId, supplierId);
        // create new product_supplier if not exist
        if (productsSuppliers == null) {
            productsSuppliers = new ProductsSuppliers(productId, supplierId);
            return ProductSupplierDAO.addProductSupplier(productsSuppliers);
        } else {
            // if exist, return the id
            return productsSuppliers.getProductSupplierId();
        }
    }

    private List<ProductsSuppliers> compareAndUpdateProductSupplierIds(ProductsSuppliers[] selectedProductSupplierIds, ProductsSuppliers[] previousProductSupplierIds) {
        List<ProductsSuppliers> idToAdd = new ArrayList<>();

        // find the minimum size of both lists
        int selectedSize = findActualSize(selectedProductSupplierIds);
        int previousSize = findActualSize(previousProductSupplierIds);
        int minSize = Math.min(selectedSize, previousSize);

        for (int i = 0; i < minSize; i++) {
            if (i > 2) {
                break;
            }

            ProductsSuppliers selected = selectedProductSupplierIds[i];
            ProductsSuppliers previous = previousProductSupplierIds[i];

            if (selected == null || previous == null) {
                continue;
            }

            if (!Objects.equals(Optional.of(previous.getProductId()), Optional.of(selected.getProductId())) ||
                    !Objects.equals(Optional.of(previous.getSupplierId()), Optional.of(selected.getSupplierId()))) {
                PackagesProductsSuppliersDAO.deletePackageProductSupplierByProductSupplierId(Integer.valueOf(previous.getProductSupplierId()));
                idToAdd.add(selected);
            }
        }

        if (selectedSize > previousSize) {
            for (int i = previousSize; i < selectedSize; i++) {
                if (selectedProductSupplierIds[i] != null) {
                    idToAdd.add(selectedProductSupplierIds[i]);
                }
            }
        }

        return idToAdd;
    }

    private int findActualSize(ProductsSuppliers[] productsSuppliers) {
        int actualSize = 0;
        for (ProductsSuppliers productsSupplier : productsSuppliers) {
            if (productsSupplier != null) {
                actualSize++;
            }
        }
        return actualSize;
    }

    /**
     * Validate fields from the form
     *
     * @return - true/false depending on the validity of fields
     */
    private boolean validateForm() {
        boolean isValid = true;
        // Validate each field using the Validator class
        isValid &= validateField(tfPackageName, Validator.checkForEmpty(tfPackageName.getText(),"Package"));
        isValid &= validateField(dpStartDate, dpStartDate.getValue() == null ? "Start date is required" : null);
        isValid &= validateField(dpEndDate, dpEndDate.getValue() == null ? "End date is required" : null);
        isValid &= validateField(tfDesc, Validator.checkForEmpty(tfDesc.getText(),"Description"));
        isValid &= validateField(tfBasePrice,Validator.checkForEmpty(tfBasePrice.getText(),"BasePrice"));
        isValid &= validateField(tfCommission,Validator.checkForEmpty(tfCommission.getText(),"Commission"));
        isValid &= validateField(dpStartDate, dpStartDate.getValue() == null ? "End date is required" : null);
        isValid &= validateField(tfDesc, Validator.checkForEmpty(tfDesc.getText()));
        isValid &= validateField(tfBasePrice, Validator.checkForEmpty(tfBasePrice.getText()));
        isValid &= validateField(tfCommission, Validator.checkForEmpty(tfCommission.getText()));
        isValid &= validateField(dpStartDate, isStartDateValid());
        isValid &= validateField(tfBasePrice, isAgencyCommissionValid());
        isValid &= validateField(tfDestination, Validator.checkForEmpty(tfDestination.getText(),"Destination"));
        return isValid;
    }

    private String isStartDateValid() {
        if (dpStartDate.getValue() == null || dpEndDate.getValue() == null) {
            return "Both start and end dates are required";
        }
        if (dpStartDate.getValue().isAfter(dpEndDate.getValue())) {
            return "Start date cannot be after the end date";
        }
        return null;
    }

    private String isAgencyCommissionValid() {
        try {
            double basePrice = Double.parseDouble(tfBasePrice.getText());
            double commission = Double.parseDouble(tfCommission.getText());

            if (commission > basePrice) {
                return "Commission should not be greater than Base Price";
            }
        } catch (NumberFormatException e) {
            return "Base Price and Commission must be valid numbers";
        }

        return null; // No error (valid)
    }

    public void setPackageData(Packages packages) {
        //disabling ID
        tfPackageID.setDisable(true);

        tfPackageID.setText(String.valueOf(packages.getPackageid()));
        tfPackageName.setText(String.valueOf(packages.getPkgname()));
        dpStartDate.setValue(packages.getPkgstartdate().get());
        dpEndDate.setValue(packages.getPkgenddate().get());
        tfDesc.setText(String.valueOf(packages.getPkgdesc()));
        tfBasePrice.setText(String.valueOf(packages.getPkgbaseprice()));
        tfCommission.setText(String.valueOf(packages.getPkgagencycommission()));
        tfPhotoURL.setText(packages.getPhoto_url() != null ? packages.getPhoto_url() : "");
        tfDestination.setText(String.valueOf(packages.getDestination()));

        // set current package id
        currentPackageId = packages.getPackageid();
    }

    @FXML
    private void onExit() {
        AddEditProductSupplierController.clearSelectedProductSuppliers();
        AddEditProductSupplierController.clearPreviousProductSupplierIds();

        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
