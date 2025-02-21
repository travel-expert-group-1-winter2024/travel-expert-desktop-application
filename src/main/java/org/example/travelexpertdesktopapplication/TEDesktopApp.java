package org.example.travelexpertdesktopapplication;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.travelexpertdesktopapplication.controllers.LoginController;
import org.example.travelexpertdesktopapplication.dao.DatabaseManager;
import org.example.travelexpertdesktopapplication.dao.UserDAO;
import org.example.travelexpertdesktopapplication.services.AuthService;
import org.tinylog.Logger;
import org.tinylog.configuration.Configuration;


import java.io.IOException;

public class TEDesktopApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //! Havent removed this old code yet, haven't decided if I still need it or not.
//        //Loading the dashboard view and controller
//        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/views/dashboard-view.fxml"));
//        //Grabbing the root element in the view, such as a AnchorPane or Vbox.
//        Parent dashboardRoot = dashboardLoader.load();
//        DashboardController dashboardController = dashboardLoader.getController();
//
//        //Repeating the steps to grab the Agent view and drawer
//        FXMLLoader agentDrawerLoader = new FXMLLoader(getClass().getResource("/views/agent-drawer-view.fxml"));
//        Parent agentDrawerRoot = agentDrawerLoader.load();
//        AgentDrawerController agentDrawerController = agentDrawerLoader.getController();
//
//
//        //Repeating to grab the Manager view and drawer
//        FXMLLoader managerDrawerLoader = new FXMLLoader(getClass().getResource("/views/manager-drawer-view.fxml"));
//        Parent managerDrawerRoot = managerDrawerLoader.load();
//        ManagerDrawerController managerDrawerController = managerDrawerLoader.getController();
//
//        FXMLLoader dashboardButtonLoader = new FXMLLoader(getClass().getResource("/views/dashboard-button-view.fxml"));
//        Parent dashboardButtonRoot = dashboardButtonLoader.load();
//        DashboardButtonController dashboardButtonController = dashboardButtonLoader.getController();
//
//        //Instantiating the CentralController and setting the references to the other controllers
//        dashboardController.setAgentDrawerController(agentDrawerController);
//        dashboardController.setManagerDrawerController(managerDrawerController);
//        dashboardController.setDashboardButtonController(dashboardButtonController);
//
//        // Inject the DashboardController into the AgentDrawerController
//        agentDrawerController.setDashboardController(dashboardController);
//        managerDrawerController.setDashboardController(dashboardController);
//        dashboardButtonController.setDashboardController(dashboardController);



        // test connection since the beginning of application
        if (!DatabaseManager.testConnection()) {
            System.exit(1);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/package-list-view.fxml"));
//        fxmlLoader.setControllerFactory(param -> new LoginController(new AuthService(new UserDAO())));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to Travel Expert!");
        //stage.setMaximized(true);
        //stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Logger.info("Application started successfully!");
    }

    public static void main(String[] args) {
        launch();
    }
}