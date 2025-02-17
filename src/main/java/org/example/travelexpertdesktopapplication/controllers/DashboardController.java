package org.example.travelexpertdesktopapplication.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardController {
    //References to other controllers.
    private AgentDrawerController agentDrawerController;
    private ManagerDrawerController managerDrawerController;
    private DashboardButtonController dashboardButtonController;
    private BaseDrawerController baseDrawerController;

    public void setBaseDrawerController(BaseDrawerController baseDrawerController) {
        this.baseDrawerController = baseDrawerController;
    }

    //Setters
    public void setAgentDrawerController(AgentDrawerController agentDrawerController) {
        this.agentDrawerController = agentDrawerController;
    }

    public void setManagerDrawerController(ManagerDrawerController managerDrawerController) {
        this.managerDrawerController = managerDrawerController;
    }

    public void setDashboardButtonController(DashboardButtonController dashboardButtonController) {
        this.dashboardButtonController = dashboardButtonController;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPaneMainView;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private StackPane stackPaneContentArea;

    @FXML
    private JFXButton tabBtn1;

    @FXML
    private JFXButton tabBtn2;

    boolean isManager = false;

    @FXML
    void initialize() {
        assert borderPaneMainView != null : "fx:id=\"borderPaneMainView\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert drawer != null : "fx:id=\"drawer\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert hamburger != null : "fx:id=\"hamburger\" was not injected: check your FXML file 'dashboard-view.fxml'.";
        assert stackPaneContentArea != null : "fx:id=\"stackPaneContentArea\" was not injected: check your FXML file 'dashboard-view.fxml'.";

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

    public void populateDashboardButtons(int code){
        if (dashboardButtonController != null){
            dashboardButtonController.updateDashBoardButtons(code);
        } else{
            System.out.println("The null pointer is here.");
        }
    }



}//class
