package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.AgencyDAO;
import org.example.travelexpertdesktopapplication.models.Agency;
import org.example.travelexpertdesktopapplication.utils.Validator;

import static org.example.travelexpertdesktopapplication.utils.ValidateFields.validateField;

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

//    @FXML
//    private Label lblErrorAddress, lblErrorCity, lblErrorProvince, lblErrorPostal, lblErrorCountry, lblErrorPhone, lblErrorFax;

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
        boolean isValid = true; // Tracks if all fields are valid
        isValid &= validateField(txtAgencyAddress, Validator.checkForEmpty(txtAgencyAddress.getText(),"Address"));
        isValid &= validateField(txtAgencyCity, Validator.checkForEmpty(txtAgencyCity.getText(),"City"));
        isValid &= validateField(txtAgencyProv, Validator.checkForEmpty(txtAgencyProv.getText(),"Province"));
        isValid &= validateField(txtAgencyPostal, Validator.validatePostalCode(txtAgencyPostal.getText())); // Validate Postal Code
        isValid &= validateField(txtAgencyCountry, Validator.checkForEmpty(txtAgencyCountry.getText(),"Country")); // Validate Country
        isValid &= validateField(txtAgencyPhone, Validator.validatePhoneNumber(txtAgencyPhone.getText()));  // Validate Phone
        isValid &= validateField(txtAgencyFax, Validator.validatePhoneNumber(txtAgencyFax.getText()));
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