/**
 * Sample Skeleton for 'logic-view.fxml' Controller Class
 */

package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.travelexpertdesktopapplication.auth.User;
import org.example.travelexpertdesktopapplication.services.AuthService;

public class LoginController {
    private final AuthService authService = new AuthService();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnLogin"
    private Button btnLogin; // Value injected by FXMLLoader

    @FXML // fx:id="lblLoginErrorMessage"
    private Label lblLoginErrorMessage; // Value injected by FXMLLoader

    @FXML // fx:id="tfPassword"
    private TextField tfPassword; // Value injected by FXMLLoader

    @FXML // fx:id="tfUsername"
    private TextField tfUsername; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'logic-view.fxml'.";
        assert lblLoginErrorMessage != null : "fx:id=\"lblLoginErrorMessage\" was not injected: check your FXML file 'logic-view.fxml'.";
        assert tfPassword != null : "fx:id=\"tfPassword\" was not injected: check your FXML file 'logic-view.fxml'.";
        assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'logic-view.fxml'.";

        lblLoginErrorMessage.setText("");
        lblLoginErrorMessage.setVisible(false);
    }

    @FXML
    void onLoginButtonClicked(MouseEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        Optional<User> user = authService.login(username, password);
        lblLoginErrorMessage.setVisible(true);
        if (user.isPresent()) {
            lblLoginErrorMessage.setStyle("-fx-text-fill: green;");
            lblLoginErrorMessage.setText("Login successful! Welcome, " + user.get().getFirstName());
        } else {
            lblLoginErrorMessage.setStyle("-fx-text-fill: red;");
            lblLoginErrorMessage.setText("Invalid username or password.");
        }
    }

}
