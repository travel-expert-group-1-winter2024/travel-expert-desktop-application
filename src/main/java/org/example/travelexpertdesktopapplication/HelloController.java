package org.example.travelexpertdesktopapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label statusLabel;

    public void initialize() {
        //Getting the connection from Database
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             //Dummy Select Statement for Agents
             ResultSet rs = stmt.executeQuery("select * from agents")) {

            while (rs.next()) {
                //Displaying AgentFirstName
                System.out.println(rs.getString("agtfirstname"));
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}