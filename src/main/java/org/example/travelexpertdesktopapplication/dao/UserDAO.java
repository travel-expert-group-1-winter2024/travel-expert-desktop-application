package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.auth.*;
import org.tinylog.Logger;

import java.sql.*;
import java.util.UUID;
import java.util.Optional;

/**
 * UserDAO.java
 * <p>
 * UserDAO is responsible for accessing and manipulating user data in the database.
 * It provides methods to find users by username and authenticate users.
 * <p>
 *
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

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String passwordHash = rs.getString("password_hash");
                    UserRole role = UserRole.valueOf(rs.getString("role"));
                    Integer agentId = (rs.getObject("agentid") != null) ? rs.getInt("agentid") : null;
                    Integer customerId = (rs.getObject("customerid") != null) ? rs.getInt("customerid") : null;

                    Logger.info("User found: Username={}, Role={}", username, role);
                    return Optional.of(createUser(id, username, passwordHash, role, agentId, customerId));
                } else {
                    Logger.warn("No user found with username: {}", username);
                }
            }
        } catch (SQLException e) {
            Logger.error(e, "Database error while searching for user: {}", username);
        } catch (IllegalArgumentException e) {
            Logger.error(e, "Invalid role found for user: {}", username);
        }

        return Optional.empty();
    }

    /**
     * Create a User object based on the role and database fields.
     *
     * @param id           The user's UUID.
     * @param username     The user's username.
     * @param passwordHash The user's password hash.
     * @param role         The user's role.
     * @param agentId      The user's agent ID.
     * @param customerId   The user's customer ID.
     * @return A User object based on the role and database fields.
     */
    private User createUser(UUID id, String username, String passwordHash, UserRole role, Integer agentId, Integer customerId) {
        return switch (role) {
            case ADMIN -> new Admin(id, username, passwordHash);
            case AGENT -> new Agent(id, username, passwordHash, agentId);
            case MANAGER -> new AgentManager(id, username, passwordHash, agentId);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

}
