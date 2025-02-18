package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.auth.*;

import java.sql.*;
import java.util.UUID;
import java.util.Optional;

/**
 * UserDAO.java
 * <p>
 * UserDAO is responsible for accessing and manipulating user data in the database.
 * It provides methods to find users by username and authenticate users.
 * <p>
 * @version 1.0.0
 */
public class UserDAO {

    /**
     * Find a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the User if found, or an empty Optional otherwise.
     */
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password_hash, role, agentid, customerid FROM users WHERE username = ?";
        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String passwordHash = rs.getString("password_hash");
                UserRole role = UserRole.valueOf(rs.getString("role"));
                Integer agentId = (rs.getObject("agentid") != null) ? rs.getInt("agentid") : null;
                Integer customerId = (rs.getObject("customerid") != null) ? rs.getInt("customerid") : null;

                return Optional.of(createUser(id, username, passwordHash, role, agentId, customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.releaseConnection(conn);
        }

        return Optional.empty();
    }

    /**
     * Create a User object based on the role and database fields.
     * @param id The user's UUID.
     * @param username The user's username.
     * @param passwordHash The user's password hash.
     * @param role The user's role.
     * @param agentId The user's agent ID.
     * @param customerId The user's customer ID.
     * @return A User object based on the role and database fields.
     */
    private User createUser(UUID id, String username, String passwordHash, UserRole role, Integer agentId, Integer customerId) {
        switch (role) {
            case ADMIN:
                return new Admin(id, username, passwordHash);
            case AGENT:
                return new Agent(id, username, passwordHash, agentId);
            case MANAGER:
                return new AgentManager(id, username, passwordHash, agentId);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

    /**
     * Authenticate a user based on their username and password.
     * @param username The user's username.
     * @param password The user's password.
     * @return An Optional containing the User if authenticated, or an empty Optional otherwise.
     */
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().authenticate(password)) {
            return userOpt;
        }
        return Optional.empty();
    }
}
