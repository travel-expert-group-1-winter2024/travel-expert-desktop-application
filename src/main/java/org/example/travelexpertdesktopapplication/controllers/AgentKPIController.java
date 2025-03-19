package org.example.travelexpertdesktopapplication.controllers;

import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.dao.AgentKPIDAO;
import org.example.travelexpertdesktopapplication.models.AgentDashboardKPI;
import org.example.travelexpertdesktopapplication.models.DestinationCount;
import org.example.travelexpertdesktopapplication.models.MonthlyBookingCount;

import static org.example.travelexpertdesktopapplication.utils.DataFormatter.formatDestinationName;

public class AgentKPIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Number> barChart;

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
    private Label labelGreeting;

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
        assert pieChart != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'agent-kpi-view.fxml'.";

        int agentId = SessionManager.getInstance().getUser().getAgentId();
        List<AgentDashboardKPI> kpiList = agentKPIDAO.getAgentKPIs(agentId);
        List<DestinationCount> destinationList = agentKPIDAO.getDestinationCount(agentId);
        List<MonthlyBookingCount> monthlyBookingList = agentKPIDAO.getMonthlyBookingCount(agentId);

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
            labelGreeting.setText("Hello " + agentName + ", Are you ready to conquer the day?");
            labelTotalCommissions.setText(currencyFormat.format(totalCommissions));
            labelTotalCustomers.setText(String.valueOf(totalCustomers));
            labelTotalSales.setText(currencyFormat.format(totalSales));


            //Pie Chart

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            pieChart.setTitle("Number of Bookings by Destination");
            for (DestinationCount destination : destinationList) {
                if (destination.destination() != null && !destination.destination().isBlank()) {
                    //Normalize destination name, as DB destinations are inconsistent.
                    String formattedDestinationName = formatDestinationName(destination.destination());
                    pieChartData.add(new PieChart.Data(formattedDestinationName, destination.count()));
                }
            }
            pieChart.setData(pieChartData);

            //Bar Chart
            barChart.setTitle("Monthly Booking Trends");
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Bookings");

            for (MonthlyBookingCount monthlyBooking : monthlyBookingList) {
                series.getData().add(new XYChart.Data<>(monthlyBooking.month(), monthlyBooking.bookingCount()));


            }


            //Add the series to the chart
            barChart.getData().clear();
            barChart.getData().add(series);






        }
    }


//    private String formatDestinationName(String destination) {
//        switch (destination) {
//            case "Athens, Greece":
//                return "Athens";
//            case "Cairo, Egypt":
//                return "Cairo";
//            case "Calcutta, India": // **Consider checking this entry**
//                return "Calcutta";
//            case "canada": // **This entry should be corrected to "Canada"**
//                return "canada"; // **Consider standardizing to "Canada"**
//            case "Canada":
//                return "Canada";
//            case "Cape Town, South Africa":
//                return "Cape Town";
//            case "Edmonton":
//                return "Edmonton";
//            case "Grande Prairie":
//                return "Grande Prairie";
//            case "Greece": // **This entry does not follow city-country format**
//                return "Greece"; // **Consider removing or adjusting this entry**
//            case "Hong Kong, China":
//                return "Hong Kong";
//            case "Houston":
//                return "Houston";
//            case "London, England":
//                return "London";
//            case "Montreal":
//                return "Montreal";
//            case "Niagara": // **This entry does not follow city-country format**
//                return "Niagara"; // **Consider removing or adjusting this entry**
//            case "NZ": // **This entry does not follow the format**
//                return "NZ"; // **Consider removing or adjusting this entry**
//            case "Paris, France":
//                return "Paris";
//            case "Peru, argentina, Bollivi": // **This entry does not follow city-country format**
//                return "Peru, argentina, Bollivi"; // **Consider fixing or clarifying this entry**
//            case "Rio de Janeiro, Brazil":
//                return "Rio de Janeiro";
//            case "Sydney, Australia":
//                return "Sydney";
//            case "Toronto":
//                return "Toronto";
//            case "USA, Mexic": // **This entry should be corrected for consistency**
//                return "USA, Mexic"; // **Consider fixing this entry**
//            case "Vancouver":
//                return "Vancouver";
//            case "Victoria":
//                return "Victoria";
//            case "Winnipeg":
//                return "Winnipeg";
//            default:
//                return destination; // Return original if not found
//        }
//    }


}//class
