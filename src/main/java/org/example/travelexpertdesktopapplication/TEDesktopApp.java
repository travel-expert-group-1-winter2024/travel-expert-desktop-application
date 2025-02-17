package org.example.travelexpertdesktopapplication;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.travelexpertdesktopapplication.controllers.AgentDrawerController;
import org.example.travelexpertdesktopapplication.controllers.CentralController;
import org.example.travelexpertdesktopapplication.controllers.DashboardController;
import org.example.travelexpertdesktopapplication.controllers.ManagerDrawerController;

import java.io.IOException;

public class TEDesktopApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Loading the dashboard view and controller
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/views/dashboard-view.fxml"));
        //Grabbing the root element in the view, such as a AnchorPane or Vbox.
        Parent dashboardRoot = dashboardLoader.load();
        DashboardController dashboardController = dashboardLoader.getController();

        //Repeating the steps to grab the Agent view and drawer
        FXMLLoader agentDrawerLoader = new FXMLLoader(getClass().getResource("/views/agent-drawer-view.fxml"));
        Parent agentDrawerRoot = agentDrawerLoader.load();
        AgentDrawerController agentDrawerController = agentDrawerLoader.getController();

        //Repeating to grab the Manager view and drawer
        FXMLLoader managerDrawerLoader = new FXMLLoader(getClass().getResource("/views/manager-drawer-view.fxml"));
        Parent managerDrawerRoot = managerDrawerLoader.load();
        ManagerDrawerController managerDrawerController = managerDrawerLoader.getController();

        //Instantiating the CentralController and setting the references to the other controllers
        CentralController centralController;
        centralController = new CentralController();
        centralController.setDashboardController(dashboardController);
        centralController.setAgentDrawerController(agentDrawerController);
        centralController.setManagerDrawerController(managerDrawerController);

        //Creating a reference to the CentralController in the other Controllers
        dashboardController.setCentralController(centralController);
        agentDrawerController.setCentralController(centralController);
        managerDrawerController.setCentralController(centralController);

        //Build scene and show
        //FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/dashboard-view.fxml"));
        Scene scene = new Scene(dashboardRoot, 1000, 600);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Travel Experts Desktop Application");
        //stage.setMaximized(true);
        //stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}