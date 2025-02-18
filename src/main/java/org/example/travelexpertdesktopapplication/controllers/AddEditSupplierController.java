package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.utils.Validator;

public class AddEditSupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<?> cbAffiliation;

    @FXML
    private Label lblAddEdit;

    @FXML
    private TextField tfPostalCode;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfBusinessNumber;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfCompanyName;

    @FXML
    private TextField tfCountry;

    @FXML
    private TextField tfEmailAddress;

    @FXML
    private TextField tfFaxNumber;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfProvince;

    @FXML
    private TextField tfSupplierContactID;

    @FXML
    private TextField tfSupplierID;

    @FXML
    private TextField tfWebsiteURL;
    @FXML
    private Label lblErrorFirstName, lblErrorLastName, lblErrorEmail, lblErrorPhone, lblErrorPostalCode;
    String mode;

    @FXML
    void initialize() {
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert cbAffiliation != null : "fx:id=\"cbAffiliation\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert lblAddEdit != null : "fx:id=\"lblAddEdit\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfPostalCode != null : "fx:id=\"tdPostalCode\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfAddress != null : "fx:id=\"tfAddress\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfBusinessNumber != null : "fx:id=\"tfBusinessNumber\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfCity != null : "fx:id=\"tfCity\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfCompanyName != null : "fx:id=\"tfCompanyName\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfCountry != null : "fx:id=\"tfCountry\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfEmailAddress != null : "fx:id=\"tfEmailAddress\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfFaxNumber != null : "fx:id=\"tfFaxNumber\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfFirstName != null : "fx:id=\"tfFirstName\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfLastName != null : "fx:id=\"tfLastName\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfProvince != null : "fx:id=\"tfProvince\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfSupplierContactID != null : "fx:id=\"tfSupplierContactID\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfSupplierID != null : "fx:id=\"tfSupplierID\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfWebsiteURL != null : "fx:id=\"tfWebsiteURL\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
    }

    public void setMode(String mode) {
        this.mode = mode;
        //change header label to current mode
        lblAddEdit.setText(mode + " Supplier");
    }

    @FXML
    private void onSaveButtonClick() {
        if (validateForm()) {
            // Proceed with saving the data
            showAlert("Success", "Supplier saved successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Validation Error", "Please correct the errors and try again.", Alert.AlertType.ERROR);
        }
    }

    private boolean validateField(TextField field, String errorMessage) {
        if (errorMessage != null) {
            // If invalid, set red border and tooltip
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            Tooltip tooltip = new Tooltip(errorMessage);
            tooltip.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12px;");
            field.setTooltip(tooltip);
            return false;
        } else {
            // If valid, reset border and remove tooltip
            field.setStyle("");
            field.setTooltip(null);
            return true;
        }
    }


    private boolean validateForm() {
        boolean isValid = true;

        // Validate each field using the Validator class
        isValid &= validateField(tfFirstName, Validator.validateName(tfFirstName.getText()));
        isValid &= validateField(tfLastName, Validator.validateName(tfLastName.getText()));
        isValid &= validateField(tfEmailAddress, Validator.validateEmail(tfEmailAddress.getText()));
        isValid &= validateField(tfBusinessNumber, Validator.validatePhoneNumber(tfBusinessNumber.getText()));
        isValid &= validateField(tfPostalCode, Validator.validatePostalCode(tfPostalCode.getText()));

        return isValid;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onExit(){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
