package org.example.travelexpertdesktopapplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.dao.UserDAO;
import org.example.travelexpertdesktopapplication.auth.UserRole;
import org.tinylog.Logger;

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
        roleChoiceBox.getItems().addAll("AGENT", "MANAGER", "ADMIN");
        roleChoiceBox.setValue("AGENT");
    }

    /** Handle signup action */
    @FXML
    public void onSignup(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        String selectedRole = roleChoiceBox.getValue();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRole == null) {
            showAlert("Validation Error", "All fields are required, including role selection.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Validation Error", "Passwords do not match.");
            return;
        }

        UserRole role;
        try {
            role = UserRole.valueOf(selectedRole.toUpperCase());
        } catch (IllegalArgumentException e) {
            showAlert("Error", "Invalid role selected.");
            return;
        }

        Logger.info(" Attempting to register user: {} as {}", username, role);
        boolean success = userDAO.addUser(username, password, role, null);
        if (success) {
            showAlert(" Success", "Account created successfully!");
            closeWindow();
        } else {
            showAlert(" Error", "Signup failed. Username may already exist.");
        }
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

    /** Show alert messages */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
