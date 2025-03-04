package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;

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

        isValid &= validateField(tfCustomerID, Validator.checkForEmpty(tfCustomerID.getText()));
        isValid &= validateField(tfFirstName, Validator.checkForEmpty(tfFirstName.getText()));
        isValid &= validateField(tfLastName, Validator.checkForEmpty(tfLastName.getText()));
        isValid &= validateField(tfAddress, Validator.checkForEmpty(tfAddress.getText()));
        isValid &= validateField(tfCity, Validator.checkForEmpty(tfCity.getText()));
        isValid &= validateField(tfProvince, Validator.checkForEmpty(tfProvince.getText()));
        isValid &= validateField(tfPostalCode, Validator.checkForEmpty(tfPostalCode.getText()));
        isValid &= validateField(tfCountry, Validator.checkForEmpty(tfCountry.getText()));
        isValid &= validateField(tfHomeNumber, Validator.checkForEmpty(tfHomeNumber.getText()));
        isValid &= validateField(tfBusinessNumber, Validator.checkForEmpty(tfBusinessNumber.getText()));
        isValid &= validateField(tfEmail, Validator.validateEmail(tfEmail.getText()));
        isValid &= validateField(tfAgentID, Validator.checkForEmpty(tfAgentID.getText()));

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
        SimpleIntegerProperty customerid = new SimpleIntegerProperty(Integer.parseInt(tfCustomerID.getText()));
        SimpleStringProperty custfirstname = new SimpleStringProperty(tfFirstName.getText());
        SimpleStringProperty custlastname = new SimpleStringProperty(tfLastName.getText());
        SimpleStringProperty custaddress = new SimpleStringProperty(tfAddress.getText());
        SimpleStringProperty custcity = new SimpleStringProperty(tfCity.getText());
        SimpleStringProperty custprov = new SimpleStringProperty(tfProvince.getText());
        SimpleStringProperty custpostal = new SimpleStringProperty(tfPostalCode.getText());
        SimpleStringProperty custcountry = new SimpleStringProperty(tfCountry.getText());
        SimpleStringProperty custhomephone = new SimpleStringProperty(tfHomeNumber.getText());
        SimpleStringProperty custbusphone = new SimpleStringProperty(tfBusinessNumber.getText());
        SimpleStringProperty custemail = new SimpleStringProperty(tfEmail.getText());
        SimpleIntegerProperty agentid = new SimpleIntegerProperty(Integer.parseInt(tfAgentID.getText()));
        return new Customer(customerid, custfirstname,custlastname,custaddress,custcity,custprov,
                custpostal,custcountry,custhomephone,custbusphone,custemail,agentid);
    }

    @FXML
    private void onExit(){
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
