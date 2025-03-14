package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.auth.UserRole;
import org.tinylog.Logger;

public class DashboardController {
    //References to other controllers.
    private AgentDrawerController agentDrawerController;
    private ManagerDrawerController managerDrawerController;
    private DashboardButtonController dashboardButtonController;

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

    @FXML
    void initialize() {
        assert dashBoardButtonHome != null : "fx:id=\"dashBoardButtonHome\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert drawer != null : "fx:id=\"drawer\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert hamburger != null : "fx:id=\"hamburger\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert mainContentWindow != null : "fx:id=\"mainContentWindow\" was not injected: check your FXML file 'dashboard-view.fxml'.";

        UserRole userRole = SessionManager.getInstance().getUserRole();

        if (userRole == UserRole.MANAGER) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/manager-drawer-view.fxml"));
                VBox box = loader.load();
                ManagerDrawerController managerDrawerController = loader.getController();
                managerDrawerController.setDashboardController(this); // Inject the DashboardController
                drawer.setSidePane(box);
                this.managerDrawerController = managerDrawerController; // Save the instance for reuse
                //Load Agent KPI Dashboard when launching.

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //loadOverViewView(UserRole.MANAGER);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/agent-drawer-view.fxml"));
                VBox box = loader.load();
                AgentDrawerController agentDrawerController = loader.getController();
                agentDrawerController.setDashboardController(this); // Inject the DashboardController
                drawer.setSidePane(box);
                this.agentDrawerController = agentDrawerController; // Save the instance for reuse
                //Load Agent KPI Dashboard when launching.

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
           // loadOverViewView(UserRole.AGENT);
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
    public void loadOverViewView(UserRole userRole){
        if (userRole == UserRole.MANAGER) {
            Logger.info("Loading Manager Over View");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sales-dashboard.fxml"));
            try {
                AnchorPane packageListView = loader.load();
                mainContentWindow.getChildren().clear();
                mainContentWindow.getChildren().add(packageListView);
                packageListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
            } catch (IOException e) {
                System.out.println("Error is being caught in the Catch of Agent-kpi-view.fxml");
                throw new RuntimeException(e);
            }
        } else{
            Logger.info("Loading Agent Over View");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/agent-kpi-view.fxml"));
            try {
                MFXScrollPane packageListView = loader.load();
                mainContentWindow.getChildren().clear();
                mainContentWindow.getChildren().add(packageListView);
                packageListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
            } catch (IOException e) {
                System.out.println("Error is being caught in the Catch of Agent-kpi-view.fxml");
                throw new RuntimeException(e);
            }
        }
    }

    //Customer Hamburger Menu Button --> Dashboard Button methods
    public void loadCustomerDetailsView(){
        Logger.info("Loading Customer Details");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/customer-view.fxml"));
        try {
            AnchorPane packageListView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(packageListView);
            packageListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            System.out.println("Error is being caught in the Catch of loadProductsView");
            throw new RuntimeException(e);
        }
    }

    public void loadCustomerPurchasesView(){
        Logger.info("Loading Customer Purchases");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/past-booking-view.fxml"));
        try {
            BorderPane packageListView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(packageListView);
            packageListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            System.out.println("Error is being caught in the Catch of past-booking-view.fxml");
            throw new RuntimeException(e);
        }
    }

    //Packages Hamburger Menu Button --> Dashboard Button methods
    public void loadPackagesView(){

        System.out.println("Loading Packages View");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/package-list-view.fxml"));
        try {
            AnchorPane packageListView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(packageListView);
            packageListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            System.out.println("Error is being caught in the Catch of loadProductsView");
            throw new RuntimeException(e);
        }

    }

    public void loadPackageDetailsView(){
        Logger.info("Loading Package Details");
    }

    //Suppliers Hamburger Menu Button --> Dashboard Button methods
    public void loadSuppliersView(){
        System.out.println("Loading Suppliers View");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/supplier-list-view.fxml"));
        try {
            AnchorPane supplierListView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(supplierListView);
            supplierListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            Logger.error(e, "Error loading SuppliersView");
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
            Logger.error(e, "Error loading ProductsView");
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
            Logger.error(e, "Error loading AgentsView");
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
            Logger.error(e, "Error loading AgenciesView");
            throw new RuntimeException(e);
        }
    }

    public void loadChatView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chat-view.fxml"));
        try {
            AnchorPane chatView = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(chatView);

            // Add the stylesheet to the productsView
            chatView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            Logger.error(e, "Error loading ChatView");
            throw new RuntimeException(e);
        }
    }

    //Manager Dashboard controls

    public void loadSalesDashboardView() {
        System.out.println("loadSalesDashboardView is also working");
        // Load the FXML file for the Sales Dashboard view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sales-dashboard.fxml"));
        try {
            AnchorPane salesDashboard = loader.load();

            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(salesDashboard);

            salesDashboard.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

        } catch (IOException e) {
            Logger.error(e, "Error loading SalesDashboardView");
            throw new RuntimeException(e);
        }
    }

    public void loadSalesAgentsView() {
        Logger.info("Loading SalesAgents View");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sales-agent-view.fxml"));
        try {
            AnchorPane agentSalesDashboard = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(agentSalesDashboard);

            agentSalesDashboard.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        } catch (IOException e) {
            Logger.error(e, "Error loading SalesAgentsView");
            throw new RuntimeException(e);
        }
    }

    public void loadSalesAgenciesView() {
        Logger.info("Loading SalesAgencies View");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sales-agency-view.fxml"));

        try {
            AnchorPane agencySalesDashboard = loader.load();
            mainContentWindow.getChildren().clear();
            mainContentWindow.getChildren().add(agencySalesDashboard);

            agencySalesDashboard.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

        } catch (IOException e) {
            Logger.error(e, "Error loading SalesAgenciesView");
            throw new RuntimeException(e);

        }
    }


}//class
