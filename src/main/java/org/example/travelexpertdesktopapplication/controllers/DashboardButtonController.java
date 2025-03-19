package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class DashboardButtonController {
    protected DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton dashboardBtn1;

    @FXML
    private JFXButton dashboardBtn2;

    @FXML
    private JFXButton dashboardBtn3;

    @FXML
    void initialize() {
        assert dashboardBtn1 != null : "fx:id=\"dashboardBtn1\" was not injected: check your FXML file 'dashboard-button-view.fxml'.";
        assert dashboardBtn2 != null : "fx:id=\"dashboardBtn2\" was not injected: check your FXML file 'dashboard-button-view.fxml'.";
        assert dashboardBtn3 != null : "fx:id=\"dashboardBtn3\" was not injected: check your FXML file 'dashboard-button-view.fxml'.";

    }



    public void generateButtons(String[] buttonTexts, Runnable[] buttonActions) {
        //Setting the first button, assuming that the length of buttonTexts is greater than 0.
        System.out.println("Updating buttons...");
        for (int i = 0; i < buttonTexts.length; i++) {
            System.out.println("Button " + (i + 1) + ": " + buttonTexts[i]);
        }
        if(buttonTexts.length > 0){
            System.out.println("Displaying the text for the first button: " + buttonTexts[0]);
            dashboardBtn1.setText(buttonTexts[0]);
            dashboardBtn1.setOnAction(event -> buttonActions[0].run());
        } else {
            dashboardBtn1.setText("");
            dashboardBtn1.setOnAction(null);
        }
        //Setting the second button, assuming that the length of buttonTexts is greater than 1.
        if (buttonTexts.length > 1){
            dashboardBtn2.setVisible(true);
            dashboardBtn2.setText(buttonTexts[1]);
            dashboardBtn2.setOnAction(event -> buttonActions[1].run());
        } else {
            dashboardBtn2.setText("");
            dashboardBtn2.setOnAction(null);
            dashboardBtn2.setVisible(false);
        }
        // Same for Button 3, if the length is above 2.
        if (buttonTexts.length > 2){
            dashboardBtn3.setVisible(true);
            dashboardBtn3.setText(buttonTexts[2]);
            dashboardBtn3.setOnAction(event -> buttonActions[2].run());
        } else {
            dashboardBtn3.setText("");
            dashboardBtn3.setOnAction(null);
            dashboardBtn3.setVisible(false);
        }
            //dashboardController.loadDashboardButtons(buttonTexts, buttonActions);
    }



} //class
