package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;

public abstract class BaseDrawerController {
    protected DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;

    }

    //Methods handling button clicks

    @FXML
    public void handlePackagesButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Packages"},
                    new Runnable[]{
                            dashboardController::loadPackagesView,
                    }
            );
            dashboardController.loadPackagesView();
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }

    }

    @FXML
    public void handleCustomersButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Details", "Purchases"},
                    new Runnable[]{
                            dashboardController::loadCustomerDetailsView,
                            dashboardController::loadCustomerPurchasesView,
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
                    new String[]{"Suppliers"},
                    new Runnable[]{
                            dashboardController::loadSuppliersView,

                    }
            );
            dashboardController.loadSuppliersView();
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    @FXML
    public void handleProductsButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Products"},
                    new Runnable[]{
                            dashboardController::loadProductsView,


                    }
            );
            dashboardController.loadProductsView();
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    @FXML
    public void handleDashboardButtonClick() {
        if (dashboardController != null){
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Overview"},
                    new Runnable[]{
                            dashboardController::loadOverViewView,
                            //dashboardController::loadAgenciesView,

                    }
            );
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

//    @FXML
//    public void handleProfileButtonClick() {
//        if (dashboardController != null){
//            System.out.println("Delegating button population to DashboardController...");
//            dashboardController.loadDashboardButtons(
//                    new String[]{"Profile"},
//                    new Runnable[]{
//                            dashboardController::loadAgentsView,
//
//
//                    }
//            );
//        } else {
//            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
//        }
//    }

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
