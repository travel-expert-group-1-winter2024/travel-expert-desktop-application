package org.example.travelexpertdesktopapplication.dao;


import org.example.travelexpertdesktopapplication.models.AgentDashboardKPI;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AgentDashboardDAO {

    public List<AgentDashboardKPI> getAgentKPIs(int agentid){
        List<AgentDashboardKPI> kpiList = new ArrayList<>();
        String query = "SELECT a.agentid, c.customerid, bt.bookingid, bd.baseprice, bd.agencycommission " +
                "FROM agents a " +
                "JOIN customers c ON a.agentid = c.agentid " +
                "JOIN bookings bt ON c.customerid = bt.customerid " +
                "JOIN bookingdetails bd ON bt.bookingid  = bd.bookingid " +
                "WHERE a.agentid =?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, agentid); //agentID parameter

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                AgentDashboardKPI kpi = new AgentDashboardKPI();
                kpi.setBookingid(resultSet.getInt("bookingid"));
                kpi.setBaseprice(resultSet.getDouble("baseprice"));
                kpi.setAgencycommission(resultSet.getDouble("agencycommission"));
                kpi.setCustomerid(resultSet.getInt("customerid"));
                kpiList.add(kpi);
            }
            System.out.println(kpiList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kpiList;
    }
}
