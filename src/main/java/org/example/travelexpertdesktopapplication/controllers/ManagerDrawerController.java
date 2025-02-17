package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class ManagerDrawerController extends BaseDrawerController {

    @FXML
    public void handleManagerButtonClick() {
        System.out.println("Manager Button Clicked!");
        //* The below lines will be updated to reflect the appropriate controllers/views
        //FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/packages-view.fxml"));
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
        dashboardController.populateDashboardButtons(7);
    }

    public ManagerDrawerController() {
        System.out.println("ManagerDrawerController instance created: " + this);
    }


}
