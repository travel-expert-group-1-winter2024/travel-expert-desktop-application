package org.example.travelexpertdesktopapplication.controllers;

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

    boolean isManager = true;

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
                VBox box = FXMLLoader.load(getClass().getResource("/views/manager-drawer-view.fxml"));
                drawer.setSidePane(box);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                VBox box = FXMLLoader.load(getClass().getResource("/views/agent-drawer-view.fxml"));
                drawer.setSidePane(box);
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

}
