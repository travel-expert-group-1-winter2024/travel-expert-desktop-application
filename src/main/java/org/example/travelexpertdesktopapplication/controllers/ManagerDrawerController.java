package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class ManagerDrawerController extends AgentDrawerController {

    @FXML
    public void handleManagerButtonClick() {
        FXMLLoader packagesLoader = new FXMLLoader(getClass().getResource("/views/manager-view.fxml"));
        //* The below lines will be updated to reflect the appropriate controllers/views
        //Parent packagesRoot = packagesLoader.load();
        //PackagesController packagesController = packagesLoader.getController();
    }


}
