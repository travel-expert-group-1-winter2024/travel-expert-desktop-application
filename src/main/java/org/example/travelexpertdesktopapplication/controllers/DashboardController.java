package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;

public class DashboardController {
    //References to other controllers.
    private AgentDrawerController agentDrawerController;
    private ManagerDrawerController managerDrawerController;
    private DashboardButtonController dashboardButtonController;


    //Setters
//    public void setAgentDrawerController(AgentDrawerController agentDrawerController) {
//        this.agentDrawerController = agentDrawerController;
//    }
//
//    public void setManagerDrawerController(ManagerDrawerController managerDrawerController) {
//        this.managerDrawerController = managerDrawerController;
//    }
//
//    public void setDashboardButtonController(DashboardButtonController dashboardButtonController) {
//        this.dashboardButtonController = dashboardButtonController;
//    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox dashBoardButtonHome;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private StackPane mainContentWindow;

    @FXML
    private StackPane stackPaneContentArea;


    boolean isManager = true;

    @FXML
    void initialize() {
        assert dashBoardButtonHome != null : "fx:id=\"dashBoardButtonHome\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert drawer != null : "fx:id=\"drawer\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert hamburger != null : "fx:id=\"hamburger\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert mainContentWindow != null : "fx:id=\"mainContentWindow\" was not injected: check your FXML file 'dashboard-view.fxml'.";

        /**
         * Temporary logic to load admin or manager depending on credentials
         */

        if (isManager){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/manager-drawer-view.fxml"));
                VBox box = loader.load();
                ManagerDrawerController managerDrawerController = loader.getController();
                managerDrawerController.setDashboardController(this); // Inject the DashboardController
                drawer.setSidePane(box);
                this.managerDrawerController = managerDrawerController; // Save the instance for reuse
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/agent-drawer-view.fxml"));
                VBox box = loader.load();
                AgentDrawerController agentDrawerController = loader.getController();
                agentDrawerController.setDashboardController(this); // Inject the DashboardController
                drawer.setSidePane(box);
                this.agentDrawerController = agentDrawerController; // Save the instance for reuse
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();

            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

    }

    //Todo: Finish logic that implements views.



    public void loadDashboardButtons(String[] buttonTexts, Runnable[] buttonActions) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard-button-view.fxml"));
            HBox blankButtons = loader.load();
            dashBoardButtonHome.getChildren().clear();
            dashBoardButtonHome.getChildren().add(blankButtons);
            DashboardButtonController buttonController = loader.getController();
            //buttonController.setDashboardController(this);

            // Pass the button texts and actions to the controller
            buttonController.generateButtons(buttonTexts, buttonActions);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * LOADING DYNAMIC BUTTON VIEWS
     */

    //Dashboard Hamburger Button --> Dynamic button methods
    public void loadOverViewView(){
        System.out.println("Loading Overview View");
    }

    //Customer Hamburger Menu Button --> Dashboard Button methods
    public void loadCustomerDetailsView(){
        System.out.println("Loading Customers View");
    }

    public void loadCustomerPurchasesView(){
        System.out.println("Loading Customer Purchases");
    }

    //Packages Hamburger Menu Button --> Dashboard Button methods
    public void loadPackagesView(){
        System.out.println("Loading Packages View");
    }

    public void loadPackageDetailsView(){
        System.out.println("Loading Packages details view");
    }

    //Suppliers Hamburger Menu Button --> Dashboard Button methods
    public void loadSuppliersView(){
        System.out.println("Loading Suppliers View");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/supplier-list-view.fxml"));
        try {
            BorderPane supplierListView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(supplierListView);
            supplierListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            System.out.println("Error is being caught in the Catch of loadProductsView");
            throw new RuntimeException(e);
        }
    }

    public void loadProductsView(){
        System.out.println("Loading Products View");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/product-view.fxml"));
        try {
            AnchorPane productsView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(productsView);

            // Add the stylesheet to the productsView
            productsView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            System.out.println("Error is being caught in the Catch of loadProductsView");
            throw new RuntimeException(e);
        }
    }


    public void loadAgentsView() {
        System.out.println("loadAgentsView is also working");
        // Load the FXML file for the Agents view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/agents-list.fxml"));
        try {
            AnchorPane agentsView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(agentsView);
            agentsView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAgenciesView() {
        System.out.println("loadAgenciesView is also working");
        // Load the FXML file for the Agents view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/agency-list.fxml"));
        try {
            AnchorPane agencyView = loader.load();

            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(agencyView);

            agencyView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}//class
