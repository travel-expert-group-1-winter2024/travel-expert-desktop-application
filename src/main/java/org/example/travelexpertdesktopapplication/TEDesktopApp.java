package org.example.travelexpertdesktopapplication;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.travelexpertdesktopapplication.controllers.*;

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

        FXMLLoader dashboardButtonLoader = new FXMLLoader(getClass().getResource("/views/dashboard-button-view.fxml"));
        Parent dashboardButtonRoot = dashboardButtonLoader.load();
        DashboardButtonController dashboardButtonController = dashboardButtonLoader.getController();

        //Instantiating the CentralController and setting the references to the other controllers
        dashboardController.setAgentDrawerController(agentDrawerController);
        dashboardController.setManagerDrawerController(managerDrawerController);
        dashboardController.setDashboardButtonController(dashboardButtonController);

        // Inject the DashboardController into the AgentDrawerController
        agentDrawerController.setDashboardController(dashboardController);
        managerDrawerController.setDashboardController(dashboardController);
        dashboardButtonController.setDashboardController(dashboardController);




        //Build scene and show
        //FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/dashboard-view.fxml"));
        //("/views/logic-view.fxml")
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