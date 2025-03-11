package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.fx.countries.Country;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.CustomerDAO;
import org.example.travelexpertdesktopapplication.models.Customer;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Validator;

import static org.example.travelexpertdesktopapplication.utils.ValidateFields.validateField;

public class EditCustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton btnExit;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TextArea tfAddress;

    @FXML
    private TextField tfAgentID;

    @FXML
    private TextField tfBusinessNumber;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfCountry;

    @FXML
    private TextField tfCustomerID;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfHomeNumber;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfPostalCode;

    @FXML
    private TextField tfProvince;

    @FXML
    void initialize() {
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfAddress != null : "fx:id=\"tfAddress\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfAgentID != null : "fx:id=\"tfAgentID\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfBusinessNumber != null : "fx:id=\"tfBusinessNumber\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfCity != null : "fx:id=\"tfCity\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfCountry != null : "fx:id=\"tfCountry\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfCustomerID != null : "fx:id=\"tfCustomerID\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfFirstName != null : "fx:id=\"tfFirstName\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfHomeNumber != null : "fx:id=\"tfHomeNumber\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfLastName != null : "fx:id=\"tfLastName\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfPostalCode != null : "fx:id=\"tfPostalCode\" was not injected: check your FXML file 'edit-customer-view.fxml'.";
        assert tfProvince != null : "fx:id=\"tfProvince\" was not injected: check your FXML file 'edit-customer-view.fxml'.";

        tfCustomerID.setDisable(true);
        tfAgentID.setDisable(true);
        tfCustomerID.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfFirstName.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfLastName.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfAddress.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfCity.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfProvince.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfPostalCode.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfHomeNumber.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfBusinessNumber.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfEmail.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());
        tfAgentID.textProperty().addListener((obs, oldVal, newVal) -> validateCustomerForm());

    }

    @FXML
    private void onClickSave(){
        if (validateCustomerForm()) {
            Customer customerData = getDetailsOfCustomerFromForm();
                CustomerDAO.updateCustomer(customerData);
                AlertBox.showAlert("Success", "Customer updated successfully!", Alert.AlertType.INFORMATION);
                this.onExit();
        } else {
            AlertBox.showAlert("Validation Error", "Please correct the errors and try again.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Validate fields from the form
     * @return - true/false depending on the validity of fields
     */
    private boolean validateCustomerForm() {
        boolean isValid = true;

        isValid &= validateField(tfCustomerID, Validator.checkForEmpty(tfCustomerID.getText().trim(),"Customer ID"));
        isValid &= validateField(tfFirstName, Validator.validateFirstName(tfFirstName.getText().trim()));
        isValid &= validateField(tfLastName, Validator.validateLastName(tfLastName.getText().trim()));
        isValid &= validateField(tfAddress, Validator.checkForEmpty(tfAddress.getText().trim(),"Address"));
        isValid &= validateField(tfCity, Validator.checkForEmpty(tfCity.getText().trim(),"City"));
        isValid &= validateField(tfProvince, Validator.checkForEmpty(tfProvince.getText().trim(),"Province"));
        isValid &= validateField(tfPostalCode, Validator.checkForEmpty(tfPostalCode.getText().trim(),"Postal COde"));
        isValid &= validateField(tfCountry, Validator.checkForEmpty(tfCountry.getText().trim(), "Country"));
        isValid &= validateField(tfHomeNumber, Validator.checkForEmpty(tfHomeNumber.getText().trim(),"Home Phone Number"));
        isValid &= validateField(tfBusinessNumber, Validator.checkForEmpty(tfBusinessNumber.getText().trim(),"Business Phone Number"));
        isValid &= validateField(tfEmail, Validator.validateEmail(tfEmail.getText().trim()));
        isValid &= validateField(tfAgentID, Validator.checkForEmpty(tfAgentID.getText().trim(),"Agent ID"));

        return isValid;
    }




    public void setCustomerData(Customer customer) {
        //disabling ID
        tfCustomerID.setDisable(true);

        tfCustomerID.setText(String.valueOf(customer.getCustomerid()));
        tfFirstName.setText(customer.getCustfirstname());
        tfLastName.setText(customer.getCustlastname());
        tfAddress.setText(customer.getCustaddress());
        tfCity.setText(customer.getCustcity());
        tfProvince.setText(customer.getCustprov());
        tfPostalCode.setText(customer.getCustpostal());
        tfCountry.setText(customer.getCustcountry());
        tfHomeNumber.setText(customer.getCusthomephone());
        tfBusinessNumber.setText(customer.getCustbusphone());
        tfEmail.setText(customer.getCustemail());
        tfAgentID.setText(String.valueOf(customer.getAgentid()));
    }

    private Customer getDetailsOfCustomerFromForm() {
        SimpleIntegerProperty customerid = new SimpleIntegerProperty(Integer.parseInt(tfCustomerID.getText().trim()));
        SimpleStringProperty custfirstname = new SimpleStringProperty(tfFirstName.getText().trim());
        SimpleStringProperty custlastname = new SimpleStringProperty(tfLastName.getText().trim());
        SimpleStringProperty custaddress = new SimpleStringProperty(tfAddress.getText().trim());
        SimpleStringProperty custcity = new SimpleStringProperty(tfCity.getText().trim());
        SimpleStringProperty custprov = new SimpleStringProperty(tfProvince.getText().trim());
        SimpleStringProperty custpostal = new SimpleStringProperty(tfPostalCode.getText().trim());
        SimpleStringProperty custcountry = new SimpleStringProperty(tfCountry.getText().trim());
        SimpleStringProperty custhomephone = new SimpleStringProperty(tfHomeNumber.getText().trim());
        SimpleStringProperty custbusphone = new SimpleStringProperty(tfBusinessNumber.getText().trim());
        SimpleStringProperty custemail = new SimpleStringProperty(tfEmail.getText().trim());
        SimpleIntegerProperty agentid = new SimpleIntegerProperty(Integer.parseInt(tfAgentID.getText().trim()));
        return new Customer(customerid, custfirstname,custlastname,custaddress,custcity,custprov,
                custpostal,custcountry,custhomephone,custbusphone,custemail,agentid);
    }

    @FXML
    private void onExit(){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
