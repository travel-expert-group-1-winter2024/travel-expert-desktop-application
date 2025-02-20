package org.example.travelexpertdesktopapplication.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Agent;

import java.sql.*;

import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;

public class AgentsDAO {
    // Retrieve all agents from the database
    public static ObservableList<Agent> getAllAgents() {
        ObservableList<Agent> agentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM agents";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Agent agent = new Agent(
                        resultSet.getInt("agentid"),
                        resultSet.getString("agtfirstname"),
                        resultSet.getString("agtmiddleinitial"),
                        resultSet.getString("agtlastname"),
                        resultSet.getString("agtbusphone"),
                        resultSet.getString("agtemail"),
                        resultSet.getString("agtposition"),
                        resultSet.getInt("agencyid")
                );
                agentList.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agentList;
    }

    // Add a new agent to the database
    public static boolean addAgent(Agent agent) {
        String query = "INSERT INTO agents (agtfirstname, agtmiddleinitial, agtlastname, agtbusphone, agtemail, agtposition, agencyid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, agent.getAgtFirstName());
            statement.setString(2, agent.getAgtMiddleInitial());
            statement.setString(3, agent.getAgtLastName());
            statement.setString(4, agent.getAgtBusPhone());
            statement.setString(5, agent.getAgtEmail());
            statement.setString(6, agent.getAgtPosition());
            statement.setInt(7, agent.getAgencyId());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing agent in the database
    public static boolean updateAgent(Agent agent) {
        String query = "UPDATE agents SET agtfirstname = ?, agtmiddleinitial = ?, agtlastname = ?, agtbusphone = ?, agtemail = ?, agtposition = ?, agencyid = ? " +
                "WHERE agentid = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, agent.getAgtFirstName());
            statement.setString(2, agent.getAgtMiddleInitial());
            statement.setString(3, agent.getAgtLastName());
            statement.setString(4, agent.getAgtBusPhone());
            statement.setString(5, agent.getAgtEmail());
            statement.setString(6, agent.getAgtPosition());
            statement.setInt(7, agent.getAgencyId());
            statement.setInt(8, agent.getAgentID());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an agent from the database
    public static boolean deleteAgent(int agentID) {
        String query = "DELETE FROM agents WHERE agentid = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, agentID);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
