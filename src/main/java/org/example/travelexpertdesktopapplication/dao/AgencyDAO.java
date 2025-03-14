package org.example.travelexpertdesktopapplication.dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Agency;
import org.tinylog.Logger;

import java.sql.*;

import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;
public class AgencyDAO {
    // Retrieve all agencies from the database
    public static ObservableList<Agency> getAllAgencies() throws SQLException {
        ObservableList<Agency> agencyList = FXCollections.observableArrayList();
        String query = "SELECT * FROM agencies";

        Logger.debug("Fetching all agencies from the database.");
        Logger.debug("SQL Query: {}", query);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Agency agency = new Agency(
                        resultSet.getInt("agencyid"),
                        resultSet.getString("agncyaddress"),
                        resultSet.getString("agncycity"),
                        resultSet.getString("agncyprov"),
                        resultSet.getString("agncypostal"),
                        resultSet.getString("agncycountry"),
                        resultSet.getString("agncyphone"),
                        resultSet.getString("agncyfax")
                );
                agencyList.add(agency);
            }
            Logger.info("Retrieved {} agencies.", agencyList.size());
        } catch (SQLException e) {
            Logger.debug(e, "Error fetching agencies from the database.");
        }
        return agencyList;
    }

    // Add a new agency to the database
    public static boolean addAgency(Agency agency) throws SQLException {
        String query = "INSERT INTO agencies (agncyaddress, agncycity, agncyprov, agncypostal, agncycountry, agncyphone, agncyfax) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Logger.debug("Attempting to add a new agency: {}", agency);
        Logger.debug("SQL Query: {}", query);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, agency.getAgncyAddress());
            statement.setString(2, agency.getAgncyCity());
            statement.setString(3, agency.getAgncyProv());
            statement.setString(4, agency.getAgncyPostal());
            statement.setString(5, agency.getAgncyCountry());
            statement.setString(6, agency.getAgncyPhone());
            statement.setString(7, agency.getAgncyFax());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Logger.info("Agency added successfully.");
                return true;
            } else {
                Logger.warn("Failed to add agency.");
                return false;
            }
        } catch (SQLException e) {
            Logger.debug(e, "Error adding agency.");
            return false;
        }
    }

    // Update an existing agency in the database
    public static boolean updateAgency(Agency agency) throws SQLException {
        // SQL query to update an agency
        String query = "UPDATE agencies SET agncyaddress = ?, agncycity = ?, agncyprov = ?, agncypostal = ?, agncycountry = ?, agncyphone = ?, agncyfax = ? " +
                "WHERE agencyid = ?";

        Logger.debug("Attempting to update agency with ID: {}", agency.getAgencyID());
        Logger.debug("SQL Query: {}", query);
        Logger.debug("Updated Agency Data: {}", agency);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, agency.getAgncyAddress());
            statement.setString(2, agency.getAgncyCity());
            statement.setString(3, agency.getAgncyProv());
            statement.setString(4, agency.getAgncyPostal());
            statement.setString(5, agency.getAgncyCountry());
            statement.setString(6, agency.getAgncyPhone());
            statement.setString(7, agency.getAgncyFax());
            statement.setInt(8, agency.getAgencyID());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                Logger.info("Agency with ID {} updated successfully.", agency.getAgencyID());
                return true;
            } else {
                Logger.warn("No agency updated, possibly invalid ID: {}", agency.getAgencyID());
                return false;
            }
        } catch (SQLException e) {
            Logger.debug(e, "Error updating agency with ID {}", agency.getAgencyID());
            return false;
        }
    }

    // Delete an agency from the database
    public static boolean deleteAgency(int agencyID) throws SQLException {
        String query = "DELETE FROM agencies WHERE agencyid = ?";

        Logger.debug("Attempting to delete agency with ID: {}", agencyID);
        Logger.debug("SQL Query: {}", query);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, agencyID);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                Logger.info("Agency with ID {} deleted successfully.", agencyID);
                return true;
            } else {
                Logger.warn("No agency deleted, possibly invalid ID: {}", agencyID);
                return false;
            }
        } catch (SQLException e) {
            Logger.debug(e, "Error deleting agency with ID {}", agencyID);
            return false;
        }
    }

    // Method to get agency by ID
    public static String getAgencyCityById(int agencyId) throws SQLException {
        String agencyCity = null;
        String query = "SELECT agncycity FROM agencies WHERE agencyid = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, agencyId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                agencyCity = rs.getString("agncycity");
            }
        } catch (SQLException e) {
            Logger.debug(e, "Error fetching Agency City associated with ID {}", agencyId);
        }

        return agencyCity;
    }
}
