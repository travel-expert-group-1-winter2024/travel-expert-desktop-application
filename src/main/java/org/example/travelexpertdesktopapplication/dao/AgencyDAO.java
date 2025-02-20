package org.example.travelexpertdesktopapplication.dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Agency;

import java.sql.*;

import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;
public class AgencyDAO {
    // Retrieve all agencies from the database
    public static ObservableList<Agency> getAllAgencies() {
        ObservableList<Agency> agencyList = FXCollections.observableArrayList();
        String query = "SELECT * FROM agencies";

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
            System.out.println(agencyList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agencyList;
    }

    // Add a new agency to the database
    public static boolean addAgency(Agency agency) {
        String query = "INSERT INTO agencies (agncyaddress, agncycity, agncyprov, agncypostal, agncycountry, agncyphone, agncyfax) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing agency in the database
    public static boolean updateAgency(Agency agency) {
        String query = "UPDATE agencies SET agncyaddress = ?, agncycity = ?, agncyprov = ?, agncypostal = ?, agncycountry = ?, agncyphone = ?, agncyfax = ? " +
                "WHERE agencyid = ?";

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
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an agency from the database
    public static boolean deleteAgency(int agencyID) {
        String query = "DELETE FROM agencies WHERE agencyid = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, agencyID);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
