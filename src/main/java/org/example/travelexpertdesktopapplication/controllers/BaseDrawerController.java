package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;

public abstract class BaseDrawerController {
    protected DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
        //System.out.println("DashboardController set in BaseDrawerController: " + this + " with DashboardController: " + dashboardController);
    }

    //Methods handling button clicks
    //Temporary methods, as we are still waiting on the controllers and views to be completed.

    @FXML
    public void handlePackagesButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Packages", "Details"},
                    new Runnable[]{
                            dashboardController::loadAgentsView,
                            dashboardController::loadAgenciesView,
                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }

    }

    @FXML
    public void handleCustomersButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Info", "Purchases"},
                    new Runnable[]{
                            dashboardController::loadAgentsView,
                            dashboardController::loadAgenciesView,
                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }

    }

    @FXML
    public void handleSuppliersButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Suppliers", "Orders"},
                    new Runnable[]{
                            dashboardController::loadAgentsView,
                            dashboardController::loadAgenciesView,
                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    @FXML
    public void handleProductsButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Products", "Services"},
                    new Runnable[]{
                            dashboardController::loadAgentsView,
                            dashboardController::loadAgenciesView,

                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    @FXML
    public void handleReportsButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Reports", "Charts"},
                    new Runnable[]{
                            dashboardController::loadAgentsView,
                            dashboardController::loadAgenciesView,

                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    @FXML
    public void handleProfileButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Profile"},
                    new Runnable[]{
                            dashboardController::loadAgentsView,


                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    public void handleLogoutButtonClick() {
        System.out.println("Logout Clicked");
//        if (dashboardController != null){
//            System.out.println("Delegating button population to DashboardController...");
//            dashboardController.delegateDashboardButtonPopulation(
//                    new String[]{"Agents", "Agencies"},
//                    new Runnable[]{
//                            dashboardController::loadAgentsView,
//                            dashboardController::loadAgenciesView,
//
//                    }
//            );
//        } else {
//            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
//        }
    }

}//class
