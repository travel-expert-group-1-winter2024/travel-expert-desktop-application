package org.example.travelexpertdesktopapplication;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.travelexpertdesktopapplication.controllers.LoginController;
import org.example.travelexpertdesktopapplication.dao.AgentKPIDAO;
import org.example.travelexpertdesktopapplication.dao.DatabaseManager;
import org.example.travelexpertdesktopapplication.dao.UserDAO;
import org.example.travelexpertdesktopapplication.services.AuthService;
import org.tinylog.Logger;


import java.io.IOException;

public class TEDesktopApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // test connection since the beginning of application
        if (!DatabaseManager.testConnection()) {
            System.exit(1);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/logic-view.fxml"));
       fxmlLoader.setControllerFactory(param -> new LoginController(new AuthService(new UserDAO())));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to Travel Expert!");
        //stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Logger.info("Application started successfully!");
        AgentKPIDAO kpi = new AgentKPIDAO();
        kpi.getAgentKPIs(1);


    }

    public static void main(String[] args) {
        launch();
    }
}