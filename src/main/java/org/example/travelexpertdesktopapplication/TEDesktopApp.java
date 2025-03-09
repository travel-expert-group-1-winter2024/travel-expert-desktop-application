package org.example.travelexpertdesktopapplication;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.travelexpertdesktopapplication.dao.AgentDashboardDAO;
import org.example.travelexpertdesktopapplication.dao.DatabaseManager;
import org.tinylog.Logger;


import java.io.IOException;

public class TEDesktopApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // test connection since the beginning of application
        if (!DatabaseManager.testConnection()) {
            System.exit(1);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(TEDesktopApp.class.getResource("/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to Travel Expert!");
        //stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Logger.info("Application started successfully!");
        AgentDashboardDAO kpi = new AgentDashboardDAO();
        kpi.getAgentKPIs(1);


    }

    public static void main(String[] args) {
        launch();
    }
}