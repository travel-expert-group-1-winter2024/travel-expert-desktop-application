package org.example.travelexpertdesktopapplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.UserDAO;
import org.example.travelexpertdesktopapplication.auth.UserRole;
import org.example.travelexpertdesktopapplication.utils.AlertBox;
import org.example.travelexpertdesktopapplication.utils.Validator;
import org.tinylog.Logger;

import static org.example.travelexpertdesktopapplication.utils.ValidateFields.validateField;

public class SignupController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private ChoiceBox<String> roleChoiceBox;
    @FXML private Button btnSignup;
    @FXML private Button btnCancel;

    private final UserDAO userDAO = new UserDAO();

    /** Initialize UI components */
    @FXML
    public void initialize() {
        Logger.info(" SignupController initialized!");
        roleChoiceBox.getItems().addAll("AGENT", "MANAGER");
        roleChoiceBox.setValue("AGENT");
        txtUsername.textProperty().addListener((obs, oldVal, newVal) -> isFormValid());
        txtPassword.textProperty().addListener((obs, oldVal, newVal) -> isFormValid());
        txtConfirmPassword.textProperty().addListener((obs, oldVal, newVal) -> isFormValid());

    }

    /** Handle signup action */
    @FXML
    public void onSignup(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        String selectedRole = roleChoiceBox.getValue();

        if(isFormValid()) {
            if (!password.equals(confirmPassword)) {
                AlertBox.showAlert("Validation Error", "Passwords do not match.", Alert.AlertType.ERROR);
                return;
            }

            UserRole role;
            try {
                role = UserRole.valueOf(selectedRole.toUpperCase());
            } catch (IllegalArgumentException e) {
                AlertBox.showAlert("Error", "Invalid role selected.", Alert.AlertType.ERROR);
                return;
            }

            Logger.info(" Attempting to register user: {} as {}", username, role);
            boolean success = userDAO.addUser(username, password, role, null);
            if (success) {
                AlertBox.showAlert(" Success", "Account created successfully!", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                AlertBox.showAlert(" Error", "Signup failed. Username may already exist.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean isFormValid(){
        boolean isValid=true;
        isValid &= validateField(txtUsername, Validator.validateEmail(txtUsername.getText().trim()));
        isValid &= validateField(txtPassword, Validator.checkForEmpty(txtPassword.getText().trim(),"Password"));
        isValid &= validateField(txtConfirmPassword, Validator.checkForEmpty(txtConfirmPassword.getText().trim(),"Confirm Password"));
        return isValid;
    }

    /** Handle cancel action */
    @FXML
    public void onCancel(ActionEvent event) {
        Logger.info(" Cancel button clicked.");
        closeWindow();
    }

    /**Close the signup window */
    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
