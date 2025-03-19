package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;

public class ManagerDrawerController extends BaseDrawerController {

    @FXML
    public void handleManagerButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Agencies", "Agents"},
                    new Runnable[]{
                            dashboardController::loadAgenciesView,
                            dashboardController::loadAgentsView

                    }
            );
            dashboardController.loadAgenciesView();
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    public ManagerDrawerController() {
        System.out.println("ManagerDrawerController instance created: " + this);
    }

    @FXML
    public void handleDashboardButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Stats", "Agents", "Agencies"},
                    new Runnable[]{
                            dashboardController::loadSalesDashboardView,
                            dashboardController::loadSalesAgentsView,
                            dashboardController::loadSalesAgenciesView


                    }
            );
            dashboardController.loadSalesDashboardView();
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }


}
