package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.sql.SQLException;
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

import static org.example.travelexpertdesktopapplication.utils.ValidateFields.validateField;

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

        tfSupplierID.setDisable(true);
        tfSupplierContactID.setDisable(true);
        //Setting Data for ComboBox
        try{
            cbAffiliation.setItems(SupplierDAO.getAffiliations());
        }catch (SQLException e){
            AlertBox.showAlert("Error", "Error displaying Affiliations.",Alert.AlertType.ERROR);
        }
        cbProvince.setItems(FXCollections.observableArrayList(Province.values()));

        tfFirstName.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfLastName.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfCompanyName.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfCity.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfPostalCode.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfBusinessNumber.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfFaxNumber.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfCountry.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfEmailAddress.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfWebsiteURL.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        tfEmailAddress.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        cbProvince.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateForm());
        cbAffiliation.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateForm());
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
                try{
                    SupplierDAO.addSupplierContact(supplierContacts);
                    AlertBox.showAlert("Success", "Supplier Contacts saved successfully!", Alert.AlertType.INFORMATION);
                    this.onExit();
                } catch (SQLException e) {
                    AlertBox.showAlert("Error","Error Adding Supplier Conatct", Alert.AlertType.ERROR);
                }

            } else{
                try {
                    SupplierDAO.updateSupplierContact(supplierContacts);
                    AlertBox.showAlert("Success", "Supplier Contacts updated successfully!", Alert.AlertType.INFORMATION);
                    this.onExit();
                } catch (SQLException e) {
                    AlertBox.showAlert("Error","Error Updating Supplier Conatct", Alert.AlertType.ERROR);
                }
            }
        }
    }

    /**
     * Getting all the details from the form
     * @return - It returns whole model of Supplier contact details
     */
    private SupplierContacts getSupplierDetailsFromForm() {
        SimpleIntegerProperty supplierContactId = new SimpleIntegerProperty(
                tfSupplierContactID.getText().isEmpty() ? 0 : Integer.parseInt(tfSupplierContactID.getText().trim())
        );

        SimpleStringProperty supConFirstName = new SimpleStringProperty(tfFirstName.getText().trim());
        SimpleStringProperty supConLastName = new SimpleStringProperty(tfLastName.getText().trim());
        SimpleStringProperty supConCompany = new SimpleStringProperty(tfCompanyName.getText().trim());
        SimpleStringProperty supConCity = new SimpleStringProperty(tfCity.getText().trim());
        SimpleStringProperty supConProv = new SimpleStringProperty(cbProvince.getSelectionModel().getSelectedItem().getAbbreviation());
        SimpleStringProperty supConPostal = new SimpleStringProperty(tfPostalCode.getText().trim());
        SimpleStringProperty supConBusPhone = new SimpleStringProperty(tfBusinessNumber.getText().trim());
        SimpleStringProperty supConFax = new SimpleStringProperty(tfFaxNumber.getText().trim());
        SimpleStringProperty supConCountry = new SimpleStringProperty(tfCountry.getText().trim());
        SimpleStringProperty supConEmail = new SimpleStringProperty(tfEmailAddress.getText().trim());
        SimpleStringProperty supConUrl = new SimpleStringProperty(tfWebsiteURL.getText().trim());
        SimpleStringProperty affiliationId = new SimpleStringProperty(cbAffiliation.getSelectionModel().getSelectedItem());

        SimpleIntegerProperty supplierId = new SimpleIntegerProperty(
                tfSupplierID.getText().trim().isEmpty() ? 0 : Integer.parseInt(tfSupplierID.getText().trim())
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
        try {
            List<String> affiliations = SupplierDAO.getAffiliations();
            cbAffiliation.setItems(FXCollections.observableArrayList(affiliations));
            cbAffiliation.getSelectionModel().select(supplierContacts.getAffiliationid());
        } catch (SQLException e) {
            AlertBox.showAlert("Error","Error displaying Affiliations", Alert.AlertType.ERROR);
        }
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
     * Validate fields from the form
     * @return - true/false depending on the validity of fields
     */
    private boolean validateForm() {
        boolean isValid = true;
        // Validate each field using the Validator class
        isValid &= validateField(tfFirstName, Validator.validateFirstName(tfFirstName.getText().trim()));
        isValid &= validateField(tfLastName, Validator.validateLastName(tfLastName.getText().trim()));
        isValid &= validateField(tfEmailAddress, Validator.validateEmail(tfEmailAddress.getText().trim()));
        isValid &= validateField(tfBusinessNumber, Validator.validatePhoneNumber(tfBusinessNumber.getText().trim()));
        isValid &= validateField(tfFaxNumber, Validator.validatePhoneNumber(tfFaxNumber.getText().trim()));
        isValid &= validateField(tfPostalCode, Validator.validatePostalCode(tfPostalCode.getText().trim()));
        isValid &= validateField(tfAddress,Validator.checkForEmpty(tfAddress.getText().trim(),"Address"));
        isValid &= validateField(tfCompanyName,Validator.checkForEmpty(tfCompanyName.getText().trim(),"Company Name"));
        isValid &= validateField(tfCity,Validator.checkForEmpty(tfCity.getText().trim(),"City"));
        isValid &= validateField(tfCountry,Validator.checkForEmpty(tfCountry.getText().trim(),"Country"));
        isValid &= validateField(tfWebsiteURL,Validator.validateURL(tfWebsiteURL.getText().trim()));
        isValid &= validateField(cbProvince, Validator.checkForEmpty(String.valueOf(cbProvince.getValue()),"Province"));
        isValid &= validateField(cbAffiliation, Validator.checkForEmpty(String.valueOf(cbAffiliation.getValue()),"Affiliation"));
        return isValid;
    }

    @FXML
    private void onExit(){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
