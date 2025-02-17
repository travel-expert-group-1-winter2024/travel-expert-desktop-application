package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public abstract class BaseDrawerController {
    protected DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
        System.out.println("DashboardController set in BaseDrawerController: " + this + " with DashboardController: " + dashboardController);
    }

    //Methods handling button clicks
    //Temporary methods, as we are still waiting on the controllers and views to be completed.

    @FXML
    public void handlePackagesButtonClick() {
        System.out.println("Packages Button Clicked in BaseDrawerController:" + this);
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
        if (dashboardController != null){
            dashboardController.populateDashboardButtons(1);
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }

    }

    @FXML
    public void handleCustomersButtonClick() {
        System.out.println("Customers Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
        dashboardController.populateDashboardButtons(2);
    }

    @FXML
    public void handleSuppliersButtonClick() {
        System.out.println("Suppliers Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
        dashboardController.populateDashboardButtons(3);
    }

    @FXML
    public void handleProductsButtonClick() {
        System.out.println("Products Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
        dashboardController.populateDashboardButtons(4);
    }

    @FXML
    public void handleReportsButtonClick() {
        System.out.println("Reports Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
        dashboardController.populateDashboardButtons(5);
    }

    @FXML
    public void handleProfileButtonClick() {
        System.out.println("Profile Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
        dashboardController.populateDashboardButtons(6);
    }

    public void handleLogoutButtonClick() {
        System.out.println("Logout Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

}//class
