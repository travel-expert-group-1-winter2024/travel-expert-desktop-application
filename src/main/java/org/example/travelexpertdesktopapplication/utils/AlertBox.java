package org.example.travelexpertdesktopapplication.utils;

import javafx.scene.control.Alert;

public class AlertBox {
    /**
     * Error and confirm alert boxes
     * @param title - title to show for alert box
     * @param message - message for the alert box
     * @param type - type of alert box
     */
    public static void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
