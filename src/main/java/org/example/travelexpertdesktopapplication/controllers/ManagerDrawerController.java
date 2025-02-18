package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;

public class ManagerDrawerController extends BaseDrawerController {

    @FXML
    public void handleManagerButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Agents", "Agencies"},
                    new Runnable[]{
                            dashboardController::loadAgentsView,
                            dashboardController::loadAgenciesView,
                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    public ManagerDrawerController() {
        System.out.println("ManagerDrawerController instance created: " + this);
    }


}
