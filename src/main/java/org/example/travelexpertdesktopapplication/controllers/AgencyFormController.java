package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.AgencyDAO;
import org.example.travelexpertdesktopapplication.models.Agency;
import org.example.travelexpertdesktopapplication.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class AgencyFormController {

    @FXML
    private TextField txtAgencyID;

    @FXML
    private TextField txtAgencyAddress;

    @FXML
    private TextField txtAgencyCity;

    @FXML
    private TextField txtAgencyProv;

    @FXML
    private TextField txtAgencyPostal;

    @FXML
    private TextField txtAgencyCountry;

    @FXML
    private TextField txtAgencyPhone;

    @FXML
    private TextField txtAgencyFax;

    @FXML
    private Label lblAgencyID;

    @FXML
    private Button btnSave;

    private Agency agency;

    @FXML
    private Label lblErrorAddress, lblErrorCity, lblErrorProvince, lblErrorPostal, lblErrorCountry, lblErrorPhone, lblErrorFax;

    @FXML
    public void initialize() {
        if (agency == null) {
            lblAgencyID.setVisible(false);
            txtAgencyID.setVisible(false);
        }
    }

    /**
     * Sets the agency data in the form for editing.
     *
     * @param agency The agency to edit.
     */
    public void setAgencyData(Agency agency) {
            this.agency = agency;
            lblAgencyID.setVisible(true);
            txtAgencyID.setVisible(true);  // Show Agency ID when editing
            txtAgencyID.setText(String.valueOf(agency.getAgencyID()));
            txtAgencyAddress.setText(agency.getAgncyAddress());
            txtAgencyCity.setText(agency.getAgncyCity());
            txtAgencyProv.setText(agency.getAgncyProv());
            txtAgencyPostal.setText(agency.getAgncyPostal());
            txtAgencyCountry.setText(agency.getAgncyCountry());
            txtAgencyPhone.setText(agency.getAgncyPhone());
            txtAgencyFax.setText(agency.getAgncyFax());

    }

    /**
     * Handles the Save button click event.
     */
    @FXML
    private void handleSave() {
        if (validateForm()) {
            // If no errors, proceed with saving the agency
            saveAgency();
        }
    }

    /**
     * Validates form fields and shows errors below each field.
     *
     * @return true if valid, false otherwise
     */
    private boolean validateForm() {
        // Clear previous error messages
        lblErrorAddress.setText("");
        lblErrorCity.setText("");
        lblErrorProvince.setText("");
        lblErrorPostal.setText("");
        lblErrorCountry.setText("");
        lblErrorPhone.setText("");
        lblErrorFax.setText("");

        boolean isValid = true; // Tracks if all fields are valid

        // Validate Address
        String addressError = Validator.checkForEmpty(txtAgencyAddress.getText());
        if (addressError != null) {
            lblErrorAddress.setText(addressError);
            isValid = false;
        }

        // Validate City
        String cityError = Validator.checkForEmpty(txtAgencyCity.getText());
        if (cityError != null) {
            lblErrorCity.setText(cityError);
            isValid = false;
        }

        // Validate Province
        String provError = Validator.checkForEmpty(txtAgencyProv.getText());
        if (provError != null) {
            lblErrorProvince.setText(provError);
            isValid = false;
        }

        // Validate Postal Code
        String postalError = Validator.validatePostalCode(txtAgencyPostal.getText());
        if (postalError != null) {
            lblErrorPostal.setText(postalError);
            isValid = false;
        }

        // Validate Country
        String countryError = Validator.checkForEmpty(txtAgencyCountry.getText());
        if (countryError != null) {
            lblErrorCountry.setText(countryError);
            isValid = false;
        }

        // Validate Phone
        String phoneError = Validator.validatePhoneNumber(txtAgencyPhone.getText());
        if (phoneError != null) {
            lblErrorPhone.setText(phoneError);
            isValid = false;
        }

        // Validate Fax (Optional: Only show error if not empty)
        String faxText = txtAgencyFax.getText().trim();
        if (!faxText.isEmpty()) {
            String faxError = Validator.validatePhoneNumber(faxText);
            if (faxError != null) {
                lblErrorFax.setText(faxError);
                isValid = false;
            }
        }

        return isValid;
    }


    /**
     * Saves the agency to the database.
     */
    private void saveAgency() {
        // Create or update the agency object
        Agency newAgency = new Agency(
                agency == null ? 0 : agency.getAgencyID(), // Use existing ID if editing, otherwise 0
                txtAgencyAddress.getText(),
                txtAgencyCity.getText(),
                txtAgencyProv.getText(),
                txtAgencyPostal.getText(),
                txtAgencyCountry.getText(),
                txtAgencyPhone.getText(),
                txtAgencyFax.getText()
        );

        // Save to the database
        boolean success;
        if (agency == null) {
            // Add new agency
            success = AgencyDAO.addAgency(newAgency);
        } else {
            // Update existing agency
            success = AgencyDAO.updateAgency(newAgency);
        }

        if (success) {
            // Close the form
            closeForm();
        } else {
            // Show error message
            showError("Failed to save agency. Please try again.");
        }
    }


    /**
     * Displays a generic error message to the user.
     *
     * @param message The error message.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes the form.
     */
    private void closeForm() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the Cancel button click event.
     */
    @FXML
    private void handleCancel() {
        closeForm();
    }
}