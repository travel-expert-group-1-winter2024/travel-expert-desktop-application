package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import org.example.travelexpertdesktopapplication.dao.SalesDashboardDAO;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SalesDashboardController implements Initializable {

    @FXML
    private BarChart<String, Number> agencyBookingsChart;
    @FXML
    private BarChart<String, Number> topAgentsChart;
    @FXML
    private LineChart<String, Number> monthlySalesChart;
    @FXML
    private PieChart topDestinationsChart;

    private final SalesDashboardDAO salesDashboardDAO = new SalesDashboardDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAgencyBookingsChart();
        loadTopAgentsChart();
        loadMonthlySalesChart();
        loadTopDestinationsChart();
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
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Sales");

        Map<String, Double> data = salesDashboardDAO.getMonthlySales();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        monthlySalesChart.getData().add(series);
    }

    private void loadTopDestinationsChart() {
        topDestinationsChart.getData().clear();

        Map<String, Integer> data = salesDashboardDAO.getTopDestinations();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            System.out.println(entry.getKey().toString().equals(""));
            topDestinationsChart.getData().add(new PieChart.Data(entry.getKey().isEmpty() ? "Others" : entry.getKey(), entry.getValue()));
        }
    }
}
