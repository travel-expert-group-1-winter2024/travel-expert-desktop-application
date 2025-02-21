package org.example.travelexpertdesktopapplication.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.travelexpertdesktopapplication.dao.AgencyDAO;
import org.example.travelexpertdesktopapplication.dao.AgentsDAO;
import org.example.travelexpertdesktopapplication.models.Agency;
import org.example.travelexpertdesktopapplication.models.Agent;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Validator;

public class AgentsFormController {

    @FXML
    private TextField txtAgentID, txtAgentFirstName,txtAgentMiddleInitials, txtAgentLastName, txtAgentPhone, txtAgentEmail, txtAgentPosition, txtAgencyID;

    @FXML
    private Label lblAgentID, lblErrorFirstName, lblErrorLastName,lblErrorMiddleInitials, lblErrorPhone, lblErrorEmail, lblErrorPosition, lblErrorAgencyID;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Agency> cmbAgency;

    private Agent agent;

    private ObservableList<Agency> agenciesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (agent == null) {
            lblAgentID.setVisible(false);
            txtAgentID.setVisible(false);
        }

        // Load agencies into the ComboBox
        loadAgencies();

        // Set a StringConverter for the ComboBox
        cmbAgency.setConverter(new StringConverter<Agency>() {
            @Override
            public String toString(Agency agency) {
                return agency != null ? agency.getAgncyCity() : ""; // Display the city name
            }

            @Override
            public Agency fromString(String string) {
                // Convert the selected string back to an Agency object
                return cmbAgency.getItems().stream()
                        .filter(agency -> agency.getAgncyCity().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void loadAgencies() {
        agenciesList.clear();
        agenciesList.addAll(AgencyDAO.getAllAgencies());
        cmbAgency.setItems(agenciesList);
    }

    public void setAgentData(Agent agent) {
        this.agent = agent;
        lblAgentID.setVisible(true);
        txtAgentID.setVisible(true);
        txtAgentID.setText(String.valueOf(agent.getAgentID()));
        txtAgentFirstName.setText(agent.getAgtFirstName());
        txtAgentMiddleInitials.setText(agent.getAgtMiddleInitial());
        txtAgentLastName.setText(agent.getAgtLastName());
        txtAgentPhone.setText(agent.getAgtBusPhone());
        txtAgentEmail.setText(agent.getAgtEmail());
        txtAgentPosition.setText(agent.getAgtPosition());
        // Set the selected agency in the ComboBox based on the agency ID
        for (Agency agency : agenciesList) {
            if (agency.getAgencyID() == agent.getAgencyId()) {
                cmbAgency.getSelectionModel().select(agency);
                break;
            }
        }
    }

    // Method to get the selected agency ID
    public int getSelectedAgencyId() {
        Agency selectedAgency = cmbAgency.getSelectionModel().getSelectedItem();
        return selectedAgency != null ? selectedAgency.getAgencyID() : -1;
    }

    @FXML
    private void handleSave() {
        if (validateForm()) {
            saveAgent();
        }
    }

    private boolean validateForm() {
        lblErrorFirstName.setText("");
        lblErrorMiddleInitials.setText("");
        lblErrorLastName.setText("");
        lblErrorPhone.setText("");
        lblErrorEmail.setText("");
        lblErrorPosition.setText("");
        lblErrorAgencyID.setText("");

        boolean isValid = true;

        String firstNameError = Validator.validateName(txtAgentFirstName.getText());
        if (firstNameError != null) {
            lblErrorFirstName.setText(firstNameError);
            isValid = false;
        }

        String middleNameError = Validator.validateName(txtAgentMiddleInitials.getText());
        if (middleNameError != null) {
            lblErrorMiddleInitials.setText(middleNameError);
            isValid = false;
        }

        String lastNameError = Validator.validateName(txtAgentLastName.getText());
        if (lastNameError != null) {
            lblErrorLastName.setText(lastNameError);
            isValid = false;
        }

        String phoneError = Validator.validatePhoneNumber(txtAgentPhone.getText());
        if (phoneError != null) {
            lblErrorPhone.setText(phoneError);
            isValid = false;
        }

        String emailError = Validator.validateEmail(txtAgentEmail.getText());
        if (emailError != null) {
            lblErrorEmail.setText(emailError);
            isValid = false;
        }

        String positionError = Validator.checkForEmpty(txtAgentPosition.getText());
        if (positionError != null) {
            lblErrorPosition.setText(positionError);
            isValid = false;
        }

        int agencyId = getSelectedAgencyId();
        if (agencyId == -1) {
            lblErrorAgencyID.setText("Please select an agency.");
            isValid = false;
        }

        return isValid;
    }

    private void saveAgent() {
        Agent newAgent = new Agent(
                agent == null ? 0 : agent.getAgentID(),
                txtAgentFirstName.getText(),
                txtAgentMiddleInitials.getText(),
                txtAgentLastName.getText(),
                txtAgentPhone.getText(),
                txtAgentEmail.getText(),
                txtAgentPosition.getText(),
                getSelectedAgencyId()
        );

        boolean success;
        if (agent == null) {
            success = AgentsDAO.addAgent(newAgent);
        } else {
            success = AgentsDAO.updateAgent(newAgent);
        }

        if (success) {
            closeForm();
        } else {
            AlertBox.showAlert("Error", "Failed to save agent. Please try again.", Alert.AlertType.ERROR);

        }
    }

    private void closeForm() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        closeForm();
    }
}
