package org.example.travelexpertdesktopapplication.controllers;

import eu.hansolo.tilesfx.chart.ChartData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;
import org.example.travelexpertdesktopapplication.dao.AgencyDAO;
import org.example.travelexpertdesktopapplication.dao.SalesDashboardDAO;
import org.example.travelexpertdesktopapplication.models.Agency;
import org.example.travelexpertdesktopapplication.utils.AlertBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SalesAgencyDashboard implements Initializable {

    @FXML private ComboBox<Agency> agencyComboBox;
    @FXML private BarChart<String, Number> salesCommissionChart;
    @FXML private PieChart marketShareChart;

    private final SalesDashboardDAO salesDAO = new SalesDashboardDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAgencyComboBox();
        configureChartProperties();
        setupSelectionListener();

        if (!agencyComboBox.getItems().isEmpty()) {
            agencyComboBox.getSelectionModel().selectFirst();
            try {
                updateCharts(agencyComboBox.getValue().getAgencyID()); // Directly pass agency ID
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setupAgencyComboBox() {
        agencyComboBox.setTooltip(new Tooltip("Please use this dropdown menu to select different agencies"));
        try {
            agencyComboBox.setItems(AgencyDAO.getAllAgencies());
            agencyComboBox.setConverter(new StringConverter<Agency>() {
                @Override
                public String toString(Agency agency) {
                    return agency.getAgncyCity() + " Branch";
                }

                @Override
                public Agency fromString(String string) {
                    return null;
                }
            });
        }catch (SQLException e) {
            AlertBox.showAlert("Error", "Error fetching Agencies", Alert.AlertType.ERROR);
        }
    }

    private void configureChartProperties() {
        salesCommissionChart.setAnimated(false);
        salesCommissionChart.setLegendVisible(false);
    }

    private void setupSelectionListener() {
        agencyComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newAgency) -> {
                    if (newAgency != null) {
                        try {
                            updateCharts(newAgency.getAgencyID());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private void updateCharts(int agencyId) throws SQLException {
        updateSalesCommissionChart(agencyId);
        updateMarketShareChart(agencyId);
    }

    private void updateSalesCommissionChart(int agencyId) throws SQLException {
        salesCommissionChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (ChartData data : salesDAO.getSalesVsCommission(agencyId)) {
            series.getData().add(new XYChart.Data<>(data.getName(), data.getValue()));
        }

        salesCommissionChart.getData().add(series);
    }

    private void updateMarketShareChart(int agencyId) throws SQLException {
        marketShareChart.getData().clear();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (ChartData data : salesDAO.getMarketShare(agencyId)) {
            pieData.add(new PieChart.Data(
                    data.getName() + " (" + String.format("%.1f%%", data.getValue()) + ")",
                    data.getValue()
            ));
        }

        marketShareChart.setData(pieData);
    }
}