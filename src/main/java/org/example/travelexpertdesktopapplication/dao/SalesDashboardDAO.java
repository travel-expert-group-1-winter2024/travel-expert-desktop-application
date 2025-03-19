package org.example.travelexpertdesktopapplication.dao;

import eu.hansolo.tilesfx.chart.ChartData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;

public class SalesDashboardDAO {

    // 1. Get number of bookings per agency
    public Map<String, Integer> getBookingsPerAgency() {
        Map<String, Integer> agencyBookings = new HashMap<>();
        String query = "SELECT agncycity, COUNT(bookingid) as total_bookings FROM Agencies a " +
                "JOIN Agents ag ON a.agencyid = ag.agencyid " +
                "JOIN Customers c ON ag.agentid = c.agentid " +
                "JOIN Bookings b ON c.customerid = b.customerid " +
                "GROUP BY agncycity";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                agencyBookings.put(rs.getString("agncycity"), rs.getInt("total_bookings"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log properly in production
        }
        return agencyBookings;
    }

    // 2. Get top-performing agent based on sales revenue
    public Map<String, Double> getTopAgentsBySales() {
        Map<String, Double> agentSales = new HashMap<>();
        String query = "SELECT CONCAT(agtfirstname, ' ', agtlastname) as agent_name, SUM(baseprice) as total_sales " +
                "FROM Agents a " +
                "JOIN Customers c ON a.agentid = c.agentid " +
                "JOIN Bookings b ON c.customerid = b.customerid " +
                "JOIN BookingDetails bd ON b.bookingid = bd.bookingid " +
                "GROUP BY agent_name ORDER BY total_sales DESC LIMIT 5";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                agentSales.put(rs.getString("agent_name"), rs.getDouble("total_sales"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agentSales;
    }

    // 3. Get monthly sales trend
    public Map<String, Double> getMonthlySales() {
        Map<String, Double> monthlySales = new HashMap<>();
        String query = "SELECT TO_CHAR(bookingdate, 'YYYY-MM') as month, SUM(baseprice) as total_sales " +
                "FROM Bookings b " +
                "JOIN BookingDetails bd ON b.bookingid = bd.bookingid " +
                "GROUP BY month ORDER BY month";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                monthlySales.put(rs.getString("month"), rs.getDouble("total_sales"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlySales;
    }

    // 4. Get top-selling destinations
    public Map<String, Integer> getTopDestinations() {
        Map<String, Integer> topDestinations = new HashMap<>();
        String query = "SELECT destination, COUNT(bookingdetailid) as total_bookings " +
                "FROM BookingDetails " +
                "GROUP BY destination ORDER BY total_bookings DESC LIMIT 5";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                topDestinations.put(rs.getString("destination"), rs.getInt("total_bookings"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topDestinations;
    }

    // Monthly Sales Trend for an Agent
    public List<ChartData> getMonthlySalesTrend(int agentId) throws SQLException {
        List<ChartData> data = new ArrayList<>();
        String query = """
        SELECT\s
            TO_CHAR(b.bookingdate, 'YYYY-MM') AS month,  -- Use TO_CHAR for PostgreSQL
            SUM(bd.baseprice) AS total_sales
        FROM Agents a
        JOIN Customers c ON a.agentid = c.agentid
        JOIN Bookings b ON c.customerid = b.customerid
        JOIN BookingDetails bd ON b.bookingid = bd.bookingid
        WHERE a.agentid = ?
        GROUP BY TO_CHAR(b.bookingdate, 'YYYY-MM')
        ORDER BY month
       \s""";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(new ChartData(
                        rs.getString("month"),  // Use "month" as the label
                        rs.getDouble("total_sales")  // Use "total_sales" as the value
                ));
            }
        }
        return data;
    }
    // Revenue by Region for an Agent
    public List<ChartData> getRevenueByRegion(int agentId) throws SQLException {
        List<ChartData> data = new ArrayList<>();
        String query = """
            SELECT\s
                bd.regionid AS region,
                SUM(bd.baseprice) AS total_revenue
            FROM Agents a
            JOIN Customers c ON a.agentid = c.agentid
            JOIN Bookings b ON c.customerid = b.customerid
            JOIN BookingDetails bd ON b.bookingid = bd.bookingid
            WHERE a.agentid = ?
            GROUP BY bd.regionid
           \s""";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(new ChartData(
                        rs.getString("region"),
                        rs.getDouble("total_revenue")
                ));
            }
        }
        return data;
    }

    // Sales vs Commission for an Agency
    public List<ChartData> getSalesVsCommission(int agencyId) throws SQLException {
        List<ChartData> data = new ArrayList<>();
        String query = """
            SELECT\s
                'Sales' AS category,
                SUM(bd.baseprice) AS value
            FROM Agencies ag
            JOIN Agents a ON ag.agencyid = a.agencyid
            JOIN Customers c ON a.agentid = c.agentid
            JOIN Bookings b ON c.customerid = b.customerid
            JOIN BookingDetails bd ON b.bookingid = bd.bookingid
            WHERE ag.agencyid = ?
            UNION ALL
            SELECT\s
                'Commission' AS category,
                SUM(bd.agencycommission) AS value
            FROM Agencies ag
            JOIN Agents a ON ag.agencyid = a.agencyid
            JOIN Customers c ON a.agentid = c.agentid
            JOIN Bookings b ON c.customerid = b.customerid
            JOIN BookingDetails bd ON b.bookingid = bd.bookingid
            WHERE ag.agencyid = ?
           \s""";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, agencyId);
            stmt.setInt(2, agencyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(new ChartData(
                        rs.getString("category"),
                        rs.getDouble("value")
                ));
            }
        }
        return data;
    }

    // Market share of Agency
    public List<ChartData> getMarketShare(int agencyId) throws SQLException {
        List<ChartData> data = new ArrayList<>();
        String query = """
        WITH AgencySales AS (
            SELECT SUM(bd.baseprice) AS agency_sales
            FROM Agencies ag
            JOIN Agents a ON ag.agencyid = a.agencyid
            JOIN Customers c ON a.agentid = c.agentid
            JOIN Bookings b ON c.customerid = b.customerid
            JOIN BookingDetails bd ON b.bookingid = bd.bookingid
            WHERE ag.agencyid = ?
        ),
        TotalSales AS (
            SELECT SUM(bd.baseprice) AS total_sales
            FROM BookingDetails bd
        )
        SELECT 
            'Selected Agency' AS name,
            (SELECT agency_sales FROM AgencySales) / 
            (SELECT total_sales FROM TotalSales) * 100 AS value
        UNION ALL
        SELECT 
            'Other Agencies' AS name,
            100 - ((SELECT agency_sales FROM AgencySales) / 
                  (SELECT total_sales FROM TotalSales) * 100) AS value
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, agencyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(new ChartData(
                        rs.getString("name"),
                        rs.getDouble("value")
                ));
            }
        }
        return data;
    }

}
