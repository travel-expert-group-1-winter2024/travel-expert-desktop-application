package org.example.travelexpertdesktopapplication.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.travelexpertdesktopapplication.models.Agent;
import org.tinylog.Logger;

import java.sql.*;

import static org.example.travelexpertdesktopapplication.dao.DatabaseManager.getConnection;

public class AgentsDAO {
    // Retrieve all agents from the database
    public static ObservableList<Agent> getAllAgents() {
        ObservableList<Agent> agentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM agents";
        Logger.debug("Fetching all agents from the database.");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Logger.debug("Executing query: {}", query);
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
            Logger.error(e, "Error fetching agents from the database.");
        }
        return agentList;
    }

    // Add a new agent to the database
    public static boolean addAgent(Agent agent) {
        String query = "INSERT INTO agents (agtfirstname, agtmiddleinitial, agtlastname, agtbusphone, agtemail, agtposition, agencyid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Logger.debug("Adding new agent: {} {}", agent.getAgtFirstName(), agent.getAgtLastName());
        Logger.debug("Executing query: {}", query);

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
            if (rowsInserted > 0) {
                Logger.info("Agent added successfully. Name={} {}", agent.getAgtFirstName(), agent.getAgtLastName());
                return true;
            } else {
                Logger.warn("Failed to add agent: {} {}", agent.getAgtFirstName(), agent.getAgtLastName());
                return false;
            }
        } catch (SQLException e) {
            Logger.error(e, "Error adding agent: {} {}", agent.getAgtFirstName(), agent.getAgtLastName());
            return false;
        }
    }

    public static int addAgentAndGetId(Agent agent) {
        String sql = "INSERT INTO agents (agtfirstname, agtmiddleinitial, agtlastname, agtbusphone, agtemail, agtposition, agencyid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, agent.getAgtFirstName());
            stmt.setString(2, agent.getAgtMiddleInitial());
            stmt.setString(3, agent.getAgtLastName());
            stmt.setString(4, agent.getAgtBusPhone());
            stmt.setString(5, agent.getAgtEmail());
            stmt.setString(6, agent.getAgtPosition());
            stmt.setInt(7, agent.getAgencyId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Retrieve the generated AgentID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated AgentID
                    }
                }
            }
        } catch (SQLException e) {
            Logger.error(e, "Error while adding new agent: {}", agent.getAgtEmail());
        }

        return -1; // Return -1 if the insertion failed
    }

    // Update an existing agent in the database
    public static boolean updateAgent(Agent agent) {
        String query = "UPDATE agents SET agtfirstname = ?, agtmiddleinitial = ?, agtlastname = ?, agtbusphone = ?, agtemail = ?, agtposition = ?, agencyid = ? " +
                "WHERE agentid = ?";
        Logger.debug("Updating agent. ID={}", agent.getAgentID());
        Logger.debug("Executing query: {}", query);

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
            if (rowsUpdated > 0) {
                Logger.info("Agent updated successfully. ID={}, Name={} {}", agent.getAgentID(), agent.getAgtFirstName(), agent.getAgtLastName());
                return true;
            } else {
                Logger.warn("No agent updated. Possibly invalid ID: {}", agent.getAgentID());
                return false;
            }
        } catch (SQLException e) {
            Logger.error(e, "Error updating agent. ID={}", agent.getAgentID());
            return false;
        }
    }

    // Delete an agent from the database
    public static boolean deleteAgent(int agentID) {
        String query = "DELETE FROM agents WHERE agentid = ?";

        Logger.debug("Deleting agent. ID={}", agentID);
        Logger.debug("Executing query: {}", query);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, agentID);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                Logger.info("Agent deleted successfully. ID={}", agentID);
                return true;
            } else {
                Logger.warn("No agent deleted. Possibly invalid ID: {}", agentID);
                return false;
            }
        } catch (SQLException e) {
            Logger.error(e, "Error deleting agent. ID={}", agentID);
            return false;
        }
    }
}
