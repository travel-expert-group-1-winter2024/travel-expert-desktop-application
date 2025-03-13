package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.auth.UserRole;
import org.example.travelexpertdesktopapplication.services.WebSocketService;
import org.tinylog.Logger;

import java.awt.event.ActionEvent;
import java.io.IOException;

public abstract class BaseDrawerController {
    protected DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;

    }
    UserRole userRole = SessionManager.getInstance().getUserRole();

    //Methods handling button clicks

    @FXML
    public void handlePackagesButtonClick() {
        if (dashboardController != null) {
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
        if (dashboardController != null) {
            System.out.println("Delegating button population to DashboardController...");
            dashboardController.loadDashboardButtons(
                    new String[]{"Details", "Purchases"},
                    new Runnable[]{
                            dashboardController::loadCustomerDetailsView,
                            dashboardController::loadCustomerPurchasesView,

                    }
            );
            dashboardController.loadCustomerDetailsView();
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }

    }

    @FXML
    public void handleSuppliersButtonClick() {
        if (dashboardController != null) {
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
        if (dashboardController != null) {
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
        if (dashboardController != null) {
            System.out.println("Delegating button population to DashboardController...");
            System.out.println(userRole);
            if (UserRole.MANAGER.equals(userRole)) {
                dashboardController.loadDashboardButtons(
                        new String[]{"Overview"},
                        new Runnable[]{
                                () -> dashboardController.loadOverViewView(UserRole.MANAGER)
                                //dashboardController::loadAgenciesView,
                        }
                );
                dashboardController.loadOverViewView(UserRole.MANAGER);
            } else if (UserRole.AGENT.equals(userRole)) {
                dashboardController.loadDashboardButtons(
                        new String[]{"Overview"},
                        new Runnable[]{
                                () -> dashboardController.loadOverViewView(UserRole.AGENT)
                        }
                );
                dashboardController.loadOverViewView(UserRole.AGENT);
            }
        } else {
            System.err.println("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }


    public void handleChatButtonClick() {
        if (dashboardController != null) {
            dashboardController.loadDashboardButtons(
                    new String[]{"Chat"},
                    new Runnable[]{
                            dashboardController::loadChatView,
                    }
            );
        } else {
            Logger.error("Error: DashboardController is null in BaseDrawerController" + this);
        }
    }

    public void handleLogoutButtonClick(MouseEvent event) {
        Logger.info("Logging out...");

        // close websocket connection
        WebSocketService.getInstance().disconnect();

        // clear session
        SessionManager.getInstance().clearSession();

        // navigate to login screen
        navigateToLogin(event);
    }

    private void navigateToLogin(MouseEvent event) {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login-view.fxml"));
            Parent root = loader.load();

            // Get the current stage and set new scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            Logger.error(e, "Failed to load login view.");
        }
    }

}//class
