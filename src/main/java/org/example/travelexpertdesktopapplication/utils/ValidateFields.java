package org.example.travelexpertdesktopapplication.utils;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class ValidateFields {
    /**
     * Validate fields
     * @param field - name of field to show validation
     * @param errorMessage - Error to show
     * @return
     */
    public static boolean validateField(Control field, String errorMessage) {
        if (errorMessage != null) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

            Tooltip tooltip = field.getTooltip();
            if (tooltip == null) {
                tooltip = new Tooltip();
                field.setTooltip(tooltip);
            }
            tooltip.setText(errorMessage);
            tooltip.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12px;");
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setShowDuration(Duration.seconds(10));
            tooltip.setHideDelay(Duration.seconds(2));
            Tooltip.install(field, tooltip);

            Tooltip finalTooltip = tooltip;
            field.setOnMouseEntered(e -> finalTooltip.show(field, e.getScreenX(), e.getScreenY() + 10));
            field.setOnMouseExited(e -> finalTooltip.hide());

            return false;
        } else {
            field.setStyle("");  // Remove red border if no error
            field.setTooltip(null);
            return true;
        }
    }
}
