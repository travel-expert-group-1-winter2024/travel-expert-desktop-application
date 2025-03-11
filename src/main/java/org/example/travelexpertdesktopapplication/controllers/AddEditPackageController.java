package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.PackagesDAO;
import org.example.travelexpertdesktopapplication.models.Packages;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Validator;

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
    private String mode;

    @FXML
    void initialize() {
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert btnSavePackage != null : "fx:id=\"btnSavePackage\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert dpEndDate != null : "fx:id=\"dpEndDate\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert dpStartDate != null : "fx:id=\"dpStartDate\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert lblAddEditPackage != null : "fx:id=\"lblAddEditPackage\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfBasePrice != null : "fx:id=\"tfBasePrice\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfCommission != null : "fx:id=\"tfCommission\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfDesc != null : "fx:id=\"tfDesc\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfPackageID != null : "fx:id=\"tfPackageID\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";
        assert tfPackageName != null : "fx:id=\"tfPackageName\" was not injected: check your FXML file 'add-edit-package-view.fxml'.";

        tfPackageID.setDisable(true);
    }

    public void setMode(String mode) {
        this.mode = mode;
        //change header label to current mode
        lblAddEditPackage.setText(mode + " Package");
    }

    @FXML
    private void onClickSave(){
        if (validateForm()) {

            Packages packagesData = getDetailsOfPackageFromForm();
            if (mode.equalsIgnoreCase("add")) {
                PackagesDAO.addPackage(packagesData);
                AlertBox.showAlert("Success", "Package has been saved successfully!", Alert.AlertType.INFORMATION);
                this.onExit();
            }else{
                SimpleIntegerProperty packageID = new SimpleIntegerProperty(Integer.parseInt(tfPackageID.getText()));
                PackagesDAO.updatePackeDetails(packageID,packagesData);
                AlertBox.showAlert("Success", "Supplier Contacts updated successfully!", Alert.AlertType.INFORMATION);
                this.onExit();
            }
        } else {
            AlertBox.showAlert("Validation Error", "Please correct the errors and try again.", Alert.AlertType.ERROR);
        }

    }

    private Packages getDetailsOfPackageFromForm() {
        SimpleIntegerProperty packageID = new SimpleIntegerProperty(Integer.parseInt(tfPackageID.getText()));
        SimpleStringProperty pkgName = new SimpleStringProperty(tfPackageName.getText());
        SimpleObjectProperty<LocalDate> pkgstartdate = new SimpleObjectProperty(dpStartDate.getValue());
        SimpleObjectProperty<LocalDate> pkgenddate = new SimpleObjectProperty(dpEndDate.getValue());
        SimpleStringProperty pkgdesc = new SimpleStringProperty(tfDesc.getText());
        SimpleIntegerProperty pkgbaseprice = new SimpleIntegerProperty(Integer.parseInt(tfBasePrice.getText()));
        SimpleIntegerProperty pkgagencycommission = new SimpleIntegerProperty(Integer.parseInt(tfCommission.getText()));
        return new Packages(packageID,pkgName,pkgstartdate,pkgenddate,pkgdesc,pkgbaseprice,pkgagencycommission);
    }

    /**
     * Validate fields from the form
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
        isValid &= validateField(dpStartDate, isStartDateValid());
        isValid &= validateField(tfBasePrice, isAgencyCommissionValid());
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
    }

    @FXML
    private void onExit(){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
