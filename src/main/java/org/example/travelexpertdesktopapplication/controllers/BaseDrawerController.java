package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BaseDrawerController {
    protected CentralController centralController;

    public void setCentralController(CentralController centralController) {
        this.centralController = centralController;
    }

    //Methods handling button clicks
    //Temporary methods, as we are still waiting on the controllers and views to be completed.

    @FXML
    public void handlePackagesButtonClick() {
        System.out.println("Packages Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleCustomersButtonClick() {
        System.out.println("Customers Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleSuppliersButtonClick() {
        System.out.println("Suppliers Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleProductsButtonClick() {
        System.out.println("Products Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleReportsButtonClick() {
        System.out.println("Reports Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleProfileButtonClick() {
        System.out.println("Profile Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    public void handleLogoutButtonClick() {
        System.out.println("Logout Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

}//class
