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
        FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleCustomersButtonClick() {
        FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/customers-view.fxml"));
        //* The below lines will be updated to reflect the appropriate controllers/views
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleSuppliersButtonClick() {
        FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/suppliers-view.fxml"));
        //* The below lines will be updated to reflect the appropriate controllers/views
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleProductsButtonClick() {
        FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/products-view.fxml"));
        //* The below lines will be updated to reflect the appropriate controllers/views
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleReportsButtonClick() {
        FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/reports-view.fxml"));
        //* The below lines will be updated to reflect the appropriate controllers/views
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

    @FXML
    public void handleProfileButtonClick() {
        FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/profile-view.fxml"));
        //* The below lines will be updated to reflect the appropriate controllers/views
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }

}//class
