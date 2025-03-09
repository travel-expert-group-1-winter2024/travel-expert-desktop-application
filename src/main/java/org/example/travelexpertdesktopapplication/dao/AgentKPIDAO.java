package org.example.travelexpertdesktopapplication.dao;


import org.example.travelexpertdesktopapplication.models.AgentDashboardKPI;
import org.example.travelexpertdesktopapplication.models.DestinationCount;
import org.example.travelexpertdesktopapplication.models.MonthlyBookingCount;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AgentKPIDAO {

    public List<AgentDashboardKPI> getAgentKPIs(int agentid){
        List<AgentDashboardKPI> kpiList = new ArrayList<>();

        double totalBasePrice = 0.0;
        double totalAgencyCommission = 0.0;
        int totalCustomerCount = 0;

        String query = "SELECT a.agentid, a.agtfirstname , COUNT(DISTINCT c.customerid ) AS total_customers, bt.bookingid, bd.destination, bd.baseprice, bd.agencycommission " +
                "FROM agents a " +
                "JOIN customers c ON a.agentid = c.agentid " +
                "JOIN bookings bt ON c.customerid = bt.customerid " +
                "JOIN bookingdetails bd ON bt.bookingid  = bd.bookingid " +
                "WHERE a.agentid =? " +
                "GROUP BY a.agentid, a.agtfirstname, bt.bookingid, bd.destination, bd.baseprice, bd.agencycommission";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, agentid); //agentID parameter

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                AgentDashboardKPI kpi = new AgentDashboardKPI();
                kpi.setAgentFirstName(resultSet.getString("agtfirstname"));
                kpi.setBookingid(resultSet.getInt("bookingid"));
                kpi.setBaseprice(resultSet.getDouble("baseprice"));
                kpi.setAgencycommission(resultSet.getDouble("agencycommission"));
                kpi.setTotalCustomers(resultSet.getInt("total_customers"));
                kpiList.add(kpi);

                totalBasePrice += kpi.getBaseprice();
                totalAgencyCommission += kpi.getAgencycommission();

            }
            System.out.println("Total Base Price: " + totalBasePrice);
            System.out.println("Total Agency Commission: " + totalAgencyCommission);
            System.out.println("Total Customer Count: " + totalCustomerCount);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kpiList;
    }

    public List<DestinationCount> getDestinationCount(int agentid){
        List<DestinationCount> destinationCountList = new ArrayList<>();
        String query = "SELECT bd.destination, COUNT(*) as destination_count " +
                "FROM agents a " +
                "JOIN customers c ON a.agentid = c.agentid " +
                "JOIN bookings bt ON c.customerid = bt.customerid " +
                "JOIN bookingdetails bd ON bt.bookingid = bd.bookingid " +
                "WHere a.agentid = ? " +
                "GROUP BY bd.destination ";

        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, agentid);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                String destination = resultSet.getString("destination");
                int destinationCount = resultSet.getInt("destination_count");
                destinationCountList.add(new DestinationCount(destination, destinationCount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
        return destinationCountList;
    }

    public List<MonthlyBookingCount> getMonthlyBookingCount(int agentid){
        List<MonthlyBookingCount> monthlyBookingCountList = new ArrayList<>();
        String query = "SELECT TO_CHAR(bd.tripstart, 'YYYY-MM') AS month, COUNT(*) AS month_count " +
                "FROM agents a " +
                "JOIN customers c ON a.agentid = c.agentid " +
                "JOIN bookings bt ON c.customerid = bt.customerid " +
                "JOIN bookingdetails bd ON bt.bookingid = bd.bookingid " +
                "Where a.agentid = ? " +
                "GROUP BY month " +
                "ORDER BY month";

        try (Connection connection = DatabaseManager.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, agentid);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                String tripstart = resultSet.getString("month");
                int monthCount = resultSet.getInt("month_count");

                monthlyBookingCountList.add(new MonthlyBookingCount(tripstart, monthCount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return monthlyBookingCountList;
    }




}//class
