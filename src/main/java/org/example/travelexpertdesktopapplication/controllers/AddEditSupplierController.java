package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.travelexpertdesktopapplication.dao.SupplierDAO;
import org.example.travelexpertdesktopapplication.models.SupplierContacts;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Province;
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
    private ComboBox<String> cbAffiliation;

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
    private ComboBox<Province> cbProvince;

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
        assert cbProvince != null : "fx:id=\"cbProvince\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfSupplierContactID != null : "fx:id=\"tfSupplierContactID\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfSupplierID != null : "fx:id=\"tfSupplierID\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";
        assert tfWebsiteURL != null : "fx:id=\"tfWebsiteURL\" was not injected: check your FXML file 'add-edit-supplier-view.fxml'.";

        //Setting Data for ComboBox
        cbAffiliation.setItems(SupplierDAO.getAffiliations());
        cbProvince.setItems(FXCollections.observableArrayList(Province.values()));
    }

    /**
     * Setting the Mode of Operation
     * @param mode - Add/Edit
     */
    public void setMode(String mode) {
        this.mode = mode;
        //change header label to current mode
        lblAddEdit.setText(mode + " Supplier Contacts");
    }

    /**
     * On click of Save check for validation
     * Once done then check for mode and then perform function Accordingly
     */
    @FXML
    private void onSaveButtonClick() {
        if (validateForm()) {

            // Get Supplier Contact details from form
            SupplierContacts supplierContacts = getSupplierDetailsFromForm();
            if (mode.equalsIgnoreCase("add")) {
                SupplierDAO.addSupplierContact(supplierContacts);
                AlertBox.showAlert("Success", "Supplier Contacts saved successfully!", Alert.AlertType.INFORMATION);
                this.onExit();
            }else{
                SupplierDAO.updateSupplierContact(supplierContacts);
                AlertBox.showAlert("Success", "Supplier Contacts updated successfully!", Alert.AlertType.INFORMATION);
                this.onExit();
            }
        } else {
            AlertBox.showAlert("Validation Error", "Please correct the errors and try again.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Getting all the details from the form
     * @return - It returns whole model of Supplier contact details
     */
    private SupplierContacts getSupplierDetailsFromForm() {
        SimpleIntegerProperty supplierContactId = new SimpleIntegerProperty(
                tfSupplierContactID.getText().isEmpty() ? 0 : Integer.parseInt(tfSupplierContactID.getText())
        );

        SimpleStringProperty supConFirstName = new SimpleStringProperty(tfFirstName.getText());
        SimpleStringProperty supConLastName = new SimpleStringProperty(tfLastName.getText());
        SimpleStringProperty supConCompany = new SimpleStringProperty(tfCompanyName.getText());
        SimpleStringProperty supConCity = new SimpleStringProperty(tfCity.getText());
        SimpleStringProperty supConProv = new SimpleStringProperty(cbProvince.getSelectionModel().getSelectedItem().getAbbreviation());
        SimpleStringProperty supConPostal = new SimpleStringProperty(tfPostalCode.getText());
        SimpleStringProperty supConBusPhone = new SimpleStringProperty(tfBusinessNumber.getText());
        SimpleStringProperty supConFax = new SimpleStringProperty(tfFaxNumber.getText());
        SimpleStringProperty supConCountry = new SimpleStringProperty(tfCountry.getText());
        SimpleStringProperty supConEmail = new SimpleStringProperty(tfEmailAddress.getText());
        SimpleStringProperty supConUrl = new SimpleStringProperty(tfWebsiteURL.getText());
        SimpleStringProperty affiliationId = new SimpleStringProperty(cbAffiliation.getSelectionModel().getSelectedItem());

        SimpleIntegerProperty supplierId = new SimpleIntegerProperty(
                tfSupplierID.getText().isEmpty() ? 0 : Integer.parseInt(tfSupplierID.getText())
        );
        SimpleStringProperty supConAddress = new SimpleStringProperty(tfAddress.getText());

        return new SupplierContacts(
                supplierContactId, supConFirstName, supConLastName, supConCompany, supConAddress,
                supConCity, supConProv, supConPostal, supConCountry, supConBusPhone,
                supConFax, supConEmail, supConUrl, affiliationId, supplierId
        );
    }

    /**
     * While edit form setting the data in the form
     * @param supplierContacts - Object that contains all the data
     */
    public void setSupplierData(SupplierContacts supplierContacts) {
        //disabling IDs
        tfSupplierContactID.setDisable(true);
        tfSupplierID.setDisable(true);

        String storedAbbreviation = supplierContacts.getSupconprov();
        Province selectedProvince = getProvinceByAbbreviation(storedAbbreviation);

        tfSupplierContactID.setText(String.valueOf(supplierContacts.getSuppliercontactid()));
        tfFirstName.setText(supplierContacts.getSupconfirstname());
        tfLastName.setText(supplierContacts.getSupconlastname());
        tfCompanyName.setText(supplierContacts.getSupconcompany());
        tfAddress.setText(supplierContacts.getSupconaddress());
        tfCity.setText(supplierContacts.getSupconcity());
        cbProvince.getSelectionModel().select(selectedProvince);
        tfPostalCode.setText(supplierContacts.getSupconpostal());
        tfCountry.setText(supplierContacts.getSupconcountry());
        tfBusinessNumber.setText(supplierContacts.getSupconbusphone());
        tfFaxNumber.setText(supplierContacts.getSupconfax());
        tfEmailAddress.setText(supplierContacts.getSupconemail());
        tfWebsiteURL.setText(supplierContacts.getSupconurl());
        tfSupplierID.setText(String.valueOf(supplierContacts.getSupplierid()));

        List<String> affiliations = SupplierDAO.getAffiliations();
        cbAffiliation.setItems(FXCollections.observableArrayList(affiliations));
        cbAffiliation.getSelectionModel().select(supplierContacts.getAffiliationid());
    }

    /**
     * Get province name
     * @param abbreviation
     * @return
     */
    private Province getProvinceByAbbreviation(String abbreviation) {
        for (Province province : Province.values()) {
            if (province.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return province;
            }
        }
        return null;
    }

    /**
     * Validate fields
     * @param field - name of field to show validation
     * @param errorMessage - Error to show
     * @return
     */
    private boolean validateField(TextField field, String errorMessage) {
        if (errorMessage != null) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            Tooltip tooltip = field.getTooltip();
            if (tooltip == null) {
                tooltip = new Tooltip();
                field.setTooltip(tooltip);
            }
            tooltip.setText(errorMessage);
            tooltip.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12px;");
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setShowDuration(Duration.seconds(10));
            tooltip.setHideDelay(Duration.seconds(2));
            Tooltip.install(field, tooltip);

            Tooltip finalTooltip = tooltip;
            field.setOnMouseEntered(e -> finalTooltip.show(field, e.getScreenX(), e.getScreenY() + 10));
            field.setOnMouseExited(e -> finalTooltip.hide());
            return false;
        } else {
            field.setStyle("");
            field.setTooltip(null);
            return true;
        }
    }

    /**
     * Validate fields from the form
     * @return - true/false depending on the validity of fields
     */
    private boolean validateForm() {
        boolean isValid = true;
        // Validate each field using the Validator class
        isValid &= validateField(tfFirstName, Validator.validateName(tfFirstName.getText()));
        isValid &= validateField(tfLastName, Validator.validateName(tfLastName.getText()));
        isValid &= validateField(tfEmailAddress, Validator.validateEmail(tfEmailAddress.getText()));
        isValid &= validateField(tfBusinessNumber, Validator.validatePhoneNumber(tfBusinessNumber.getText()));
        isValid &= validateField(tfPostalCode, Validator.validatePostalCode(tfPostalCode.getText()));
        isValid &= validateField(tfAddress,Validator.checkForEmpty(tfAddress.getText()));
        isValid &= validateField(tfCompanyName,Validator.checkForEmpty(tfCompanyName.getText()));
        isValid &= validateField(tfCity,Validator.checkForEmpty(tfCity.getText()));
        isValid &= validateField(tfCountry,Validator.checkForEmpty(tfCountry.getText()));
        isValid &= validateField(tfWebsiteURL,Validator.validateURL(tfWebsiteURL.getText()));
        return isValid;
    }

    @FXML
    private void onExit(){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
