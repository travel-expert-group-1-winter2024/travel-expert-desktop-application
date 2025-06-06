package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.dao.SalesDashboardDAO;
import org.example.travelexpertdesktopapplication.models.AgentDashboardKPI;
import org.example.travelexpertdesktopapplication.dao.AgentKPIDAO;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.example.travelexpertdesktopapplication.utils.DataFormatter.formatDestinationName;

public class SalesDashboardController implements Initializable {

    @FXML
    private BarChart<String, Number> agencyBookingsChart;
    @FXML
    private BarChart<String, Number> topAgentsChart;
    @FXML
    private LineChart<String, Number> monthlySalesChart;
    @FXML
    private PieChart topDestinationsChart;
    @FXML
    private Label greeting;

    String agentName;

    private AgentKPIDAO agentKPIDAO = new AgentKPIDAO();

    private final SalesDashboardDAO salesDashboardDAO = new SalesDashboardDAO();
    int agentId = SessionManager.getInstance().getUser().getAgentId();

    List<AgentDashboardKPI> kpiList = agentKPIDAO.getAgentKPIs(agentId);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAgencyBookingsChart();
        loadTopAgentsChart();
        loadMonthlySalesChart();
        loadTopDestinationsChart();
        loadAgentName();
    }

    private void loadAgentName(){
        for (AgentDashboardKPI kpi : kpiList){
            agentName = kpi.getAgentFirstName();
        }
        greeting.setText("Hello " + agentName + ", Are you ready to conquer the day?");
    }

    private void loadAgencyBookingsChart() {
        agencyBookingsChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bookings per Agency");

        Map<String, Integer> data = salesDashboardDAO.getBookingsPerAgency();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        agencyBookingsChart.getData().add(series);
    }

    private void loadTopAgentsChart() {
        topAgentsChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Performing Agents");

        Map<String, Double> data = salesDashboardDAO.getTopAgentsBySales();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        topAgentsChart.getData().add(series);
    }

    private void loadMonthlySalesChart() {
        monthlySalesChart.getData().clear();

        // Create a series for each quarter
        Map<Integer, XYChart.Series<String, Number>> quarterSeries = new HashMap<>();
        for (int q = 1; q <= 4; q++) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Q" + q);
            quarterSeries.put(q, series);
        }

        Map<String, Double> data = salesDashboardDAO.getQuarterlySales();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            String[] parts = entry.getKey().split(" "); // "Q1 2023" → ["Q1", "2023"]
            int quarter = Integer.parseInt(parts[0].substring(1)); // Extract "1" from "Q1"
            String year = parts[1];
            quarterSeries.get(quarter).getData().add(new XYChart.Data<>(year, entry.getValue()));
        }

        // Add all series to the chart
        quarterSeries.values().forEach(series -> monthlySalesChart.getData().add(series));
    }

    private void loadTopDestinationsChart() {
        topDestinationsChart.getData().clear();

        Map<String, Integer> data = salesDashboardDAO.getTopDestinations();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
                System.out.println(entry.getKey().toString());
                String destinationName = formatDestinationName(entry.getKey());
                System.out.println(destinationName);
                //topDestinationsChart.getData().add(new PieChart.Data(entry.getKey().isEmpty() ? "Others" : entry.getKey(), entry.getValue()));
                topDestinationsChart.getData().add(new PieChart.Data(destinationName, entry.getValue()));

        }
    }
}
