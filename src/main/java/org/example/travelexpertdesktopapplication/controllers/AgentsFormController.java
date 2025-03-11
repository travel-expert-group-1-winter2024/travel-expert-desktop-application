package org.example.travelexpertdesktopapplication.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.travelexpertdesktopapplication.auth.UserRole;
import org.example.travelexpertdesktopapplication.dao.AgencyDAO;
import org.example.travelexpertdesktopapplication.dao.AgentsDAO;
import org.example.travelexpertdesktopapplication.dao.UserDAO;
import org.example.travelexpertdesktopapplication.models.Agency;
import org.example.travelexpertdesktopapplication.models.Agent;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Validator;
import org.tinylog.Logger;

import static org.example.travelexpertdesktopapplication.utils.ValidateFields.validateField;

public class AgentsFormController {

    @FXML
    private TextField txtAgentID, txtAgentFirstName,txtAgentMiddleInitials, txtAgentLastName, txtAgentPhone, txtAgentEmail, txtAgentPosition, txtAgencyID;

    @FXML
    private Label lblAgentID;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Agency> cmbAgency;

    private Agent agent;

    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPasswordVisible;
    @FXML private ImageView eyeIconPassword;

    @FXML private PasswordField txtConfirmPassword;
    @FXML private TextField txtConfirmPasswordVisible;
    @FXML private ImageView eyeIconConfirmPassword;

    @FXML private Label lblUppercase, lblLowercase, lblSpecialChar, lblMinLength, lblErrorConfirmPassword, lblErrorPassword;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    private ObservableList<Agency> agenciesList = FXCollections.observableArrayList();
    private UserDAO userDAO = new UserDAO();

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

        // Set initial visibility for password fields
        txtPasswordVisible.setVisible(false); // Initially hidden
        txtPassword.setVisible(true); // Initially visible

        txtConfirmPasswordVisible.setVisible(false); // Initially hidden
        txtConfirmPassword.setVisible(true); // Initially visible

        // Validate password as user types
        txtPassword.textProperty().addListener((obs, oldVal, newVal) -> validatePassword(newVal)
            );
        txtConfirmPassword.textProperty().addListener((obs, oldVal, newVal) ->
            checkPasswordMatch());

        txtPasswordVisible.textProperty().addListener((obs, oldVal, newVal) -> {validatePassword(newVal);
        txtPassword.setText(newVal);});
        txtConfirmPasswordVisible.textProperty().addListener((obs, oldVal, newVal) -> {txtConfirmPassword.setText(newVal);
            checkPasswordMatch();
        });

        txtAgentFirstName.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        txtAgentLastName.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        txtAgentPhone.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        txtAgentEmail.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        cmbAgency.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateForm());
        txtPassword.textProperty().addListener((obs,oldVal,newVal)->validateForm());

        // Set default eye icons
        eyeIconPassword.setImage(new Image(getClass().getResourceAsStream("/images/eye_closed.png")));
        eyeIconConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/images/eye_closed.png")));
    }

    private boolean validatePassword(String password) {
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+=-].*");
        boolean hasMinLength = password.length() >= 8;

        lblUppercase.setStyle("-fx-text-fill: " + (hasUppercase ? "green" : "red") + ";");
        lblLowercase.setStyle("-fx-text-fill: " + (hasLowercase ? "green" : "red") + ";");
        lblSpecialChar.setStyle("-fx-text-fill: " + (hasSpecial ? "green" : "red") + ";");
        lblMinLength.setStyle("-fx-text-fill: " + (hasMinLength ? "green" : "red") + ";");
        checkPasswordMatch();
        return hasLowercase && hasSpecial && hasMinLength && hasUppercase;
    }

    private void checkPasswordMatch() {
        if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            lblErrorConfirmPassword.setText("Passwords do not match!");
            lblErrorConfirmPassword.setStyle("-fx-text-fill: red;");
        } else {
            lblErrorConfirmPassword.setText("✔ Passwords match");
            lblErrorConfirmPassword.setStyle("-fx-text-fill: green;");
        }
    }

    @FXML
    private void togglePasswordVisibility(MouseEvent event) {
        isPasswordVisible = !isPasswordVisible;
        if (isPasswordVisible) {
            txtPasswordVisible.setText(txtPassword.getText());
            txtPasswordVisible.setVisible(true);
            txtPassword.setVisible(false);
            eyeIconPassword.setImage(new Image(getClass().getResourceAsStream("/images/eye_open.png")));
        } else {
            txtPassword.setText(txtPasswordVisible.getText());
            txtPassword.setVisible(true);
            txtPasswordVisible.setVisible(false);
            eyeIconPassword.setImage(new Image(getClass().getResourceAsStream("/images/eye_closed.png")));
        }
    }

    @FXML
    private void toggleConfirmPasswordVisibility(MouseEvent event) {
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        if (isConfirmPasswordVisible) {
            txtConfirmPasswordVisible.setText(txtConfirmPassword.getText());
            txtConfirmPasswordVisible.setVisible(true);
            txtConfirmPassword.setVisible(false);
            eyeIconConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/images/eye_open.png")));
        } else {
            txtConfirmPassword.setText(txtConfirmPasswordVisible.getText());
            txtConfirmPassword.setVisible(true);
            txtConfirmPasswordVisible.setVisible(false);
            eyeIconConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/images/eye_closed.png")));
        }
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
        boolean isValid = true;
        isValid &= validateField(txtAgentFirstName, Validator.validateFirstName(txtAgentFirstName.getText()));
        isValid &= validateField(txtAgentLastName, Validator.validateLastName(txtAgentLastName.getText()));
        isValid &= validateField(txtAgentPhone, Validator.validatePhoneNumber(txtAgentPhone.getText()));
        isValid &= validateField(txtAgentEmail, Validator.validateEmail(txtAgentEmail.getText()));
        isValid &= validateField(cmbAgency, Validator.checkForEmpty(String.valueOf(cmbAgency.getValue()),"Agency"));
        Boolean validPass = validatePassword(txtPassword.getText());
        isValid &= validateField(txtPassword, Validator.checkForEmpty(txtPassword.getText(),"Password"));


//        if (passwordError != null) {
//            lblErrorPassword.setText(passwordError);
//            isValid = false;
//        }
//        else
            if(!validPass){
//            lblErrorPassword.setText("Invalid Password");
            isValid = false;
        }


        return isValid;
    }

    private void saveAgent() {
        // Create the new agent object
        Agent newAgent = new Agent(
                agent == null ? 0 : agent.getAgentID(), // If agent is null, use 0 as a placeholder
                txtAgentFirstName.getText().trim(),
                txtAgentMiddleInitials.getText().trim().equals("") ? "" : txtAgentMiddleInitials.getText().trim(),
                txtAgentLastName.getText().trim(),
                txtAgentPhone.getText().trim(),
                txtAgentEmail.getText().trim(),
                txtAgentPosition.getText().trim().equals("") ? "" : txtAgentPosition.getText().trim(),
                getSelectedAgencyId()
        );

        boolean agentSuccess;
        int generatedAgentId = -1; // To store the generated AgentID

        if (agent == null) {
            // Add the new agent to the database and retrieve the generated AgentID
            generatedAgentId = AgentsDAO.addAgentAndGetId(newAgent); // New method to get the generated ID
            agentSuccess = (generatedAgentId != -1); // Check if the agent was added successfully
        } else {
            // Update the existing agent in the database
            agentSuccess = AgentsDAO.updateAgent(newAgent);
        }

        if (agentSuccess) {
            // If the agent was added/updated successfully, proceed to add a user
            if (agent == null) {
                // Only add a user if this is a new agent
                String username = txtAgentEmail.getText(); // Use email as the username
                String password = txtPassword.getText(); // Get the password from the password field
                UserRole role = UserRole.AGENT; // Default role for new agents

                // Add the user to the users table using the generated AgentID
                boolean userSuccess = userDAO.addUser(username, password, role, generatedAgentId);

                if (userSuccess) {
                    Logger.info("New user added for agent: Username={}, AgentID={}", username, generatedAgentId);
                } else {
                    Logger.error("Failed to add user for agent: Username={}", username);
                    AlertBox.showAlert("Error", "Agent was saved, but failed to create a user account.", Alert.AlertType.WARNING);
                }
            }

            closeForm(); // Close the form after successful save
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
