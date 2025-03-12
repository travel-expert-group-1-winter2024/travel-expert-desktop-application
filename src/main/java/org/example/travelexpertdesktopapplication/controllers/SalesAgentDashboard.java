package org.example.travelexpertdesktopapplication.controllers;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.example.travelexpertdesktopapplication.dao.AgentsDAO;
import org.example.travelexpertdesktopapplication.dao.SalesDashboardDAO;
import org.example.travelexpertdesktopapplication.models.Agent;
import org.example.travelexpertdesktopapplication.utils.AlertBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SalesAgentDashboard implements Initializable {

    @FXML private ComboBox<Agent> agentComboBox;
    @FXML private LineChart<String, Number> monthlySalesChart;
    @FXML private BarChart<String, Number> regionRevenueChart;

    private final SalesDashboardDAO salesDashboardDAO = new SalesDashboardDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAgentComboBox();
        configureChartProperties();
        setupSelectionListener();

        if (!agentComboBox.getItems().isEmpty()) {
            agentComboBox.getSelectionModel().selectFirst();
            updateCharts(agentComboBox.getValue().getAgentID());
        }
    }

    private void setupAgentComboBox() {
        try{
        agentComboBox.setItems(AgentsDAO.getAllAgents());
        agentComboBox.setConverter(new StringConverter<Agent>() {
            @Override
            public String toString(Agent agent) {
                return agent.getAgtFirstName() + " " + agent.getAgtLastName();
            }

            @Override
            public Agent fromString(String string) {
                return null; // Not needed for display
            }
        });}
        catch (SQLException e){
            AlertBox.showAlert("Error", "Error fetching Agents", Alert.AlertType.ERROR);
        }
    }

    private void configureChartProperties() {
        monthlySalesChart.setAnimated(false);
        regionRevenueChart.setAnimated(false);
        monthlySalesChart.setLegendVisible(false);
        regionRevenueChart.setLegendVisible(false);
    }

    private void setupSelectionListener() {
        agentComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newAgent) -> {
                    if (newAgent != null) {
                        updateCharts(newAgent.getAgentID());
                    }
                }
        );
    }

    private void updateCharts(int agentId) {
        try {
            // Update Monthly Sales Chart
            updateMonthlySalesChart(agentId);

            // Update Region Revenue Chart
            updateRegionRevenueChart(agentId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMonthlySalesChart(int agentId) throws SQLException {
        monthlySalesChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (ChartData data : salesDashboardDAO.getMonthlySalesTrend(agentId)) {
            series.getData().add(new XYChart.Data<>(data.getName(), data.getValue()));
        }

        monthlySalesChart.getData().add(series);
    }

    private void updateRegionRevenueChart(int agentId) throws SQLException {
        regionRevenueChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (ChartData data : salesDashboardDAO.getRevenueByRegion(agentId)) {
            series.getData().add(new XYChart.Data<>(data.getName(), data.getValue()));
        }

        regionRevenueChart.getData().add(series);
    }
}