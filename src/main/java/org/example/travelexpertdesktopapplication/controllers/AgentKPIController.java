package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.travelexpertdesktopapplication.dao.AgentKPIDAO;
import org.example.travelexpertdesktopapplication.models.AgentDashboardKPI;

public class AgentKPIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private AnchorPane dataPanel1;

    @FXML
    private AnchorPane dataPanel2;

    @FXML
    private AnchorPane dataPanel3;

    @FXML
    private AnchorPane dataPanel4;

    @FXML
    private Label labelTotalCommissions;

    @FXML
    private Text greeting;

    @FXML
    private Label labelTotalCustomers;

    @FXML
    private Label labelTotalSales;

    @FXML
    private PieChart pieChart;

    private AgentKPIDAO agentKPIDAO = new AgentKPIDAO();



    @FXML
    void initialize() {
        assert barChart != null : "fx:id=\"barChart\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert dataPanel1 != null : "fx:id=\"dataPanel1\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert dataPanel2 != null : "fx:id=\"dataPanel2\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert dataPanel3 != null : "fx:id=\"dataPanel3\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert dataPanel4 != null : "fx:id=\"dataPanel4\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert labelTotalCommissions != null : "fx:id=\"labelTotalCommissions\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert labelTotalCustomers != null : "fx:id=\"labelTotalCustomers\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert labelTotalSales != null : "fx:id=\"labelTotalSales\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert greeting != null: "fx:id=\"greeting\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";
        assert pieChart != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";

        int agentId = 1;
        List<AgentDashboardKPI> kpiList = agentKPIDAO.getAgentKPIs(agentId);

        String agentName;
        double totalCommissions = 0.0;
        int totalCustomers = 0;
        double totalSales = 0.0;

        // Populate labels and pie chart data
        for (AgentDashboardKPI kpi : kpiList) {

            agentName = kpi.getAgentFirstName();
            //agentName = "bob";
            totalCommissions += kpi.getAgencycommission();
            totalCustomers += kpi.getTotalCustomers();
            totalSales += kpi.getBaseprice();

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            // Set the labels
            greeting.setText("Hello " + agentName + ", Are you ready to conquer the day?");
            labelTotalCommissions.setText(currencyFormat.format(totalCommissions));
            labelTotalCustomers.setText(String.valueOf(totalCustomers));
            labelTotalSales.setText(currencyFormat.format(totalSales));


        }
    }







}//class
