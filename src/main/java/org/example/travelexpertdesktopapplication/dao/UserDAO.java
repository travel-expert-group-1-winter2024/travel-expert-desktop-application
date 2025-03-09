package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.auth.*;
import org.tinylog.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            case AGENT -> new Agent(id, username, passwordHash, agentId);
            case MANAGER -> new Manager(id, username, passwordHash, agentId);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

//    **************************

    /**
     *  Register a new user (Signup)
     * Supports AGENT AND MANAGER roles only.
     * @param username The user's username.
     * @param plainPassword The user's plaintext password.
     * @param role The user's role.
     * @param agentId The agent ID (nullable).
     * @return True if the user was added successfully, False otherwise.
     */
    public boolean addUser(String username, String plainPassword, UserRole role, Integer agentId) {


        String sql = "INSERT INTO users (id, username, password_hash, role, agentid) VALUES (?, ?, ?, ?, ?)";
        UUID userId = UUID.randomUUID();
        String passwordHash = hashPassword(plainPassword);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, userId);
            stmt.setString(2, username);
            stmt.setString(3, passwordHash);
            stmt.setString(4, role.name());
            stmt.setObject(5, agentId);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                Logger.info(" New user registered: Username={}, Role={}", username, role);
                return true;
            }
        } catch (SQLException e) {
            Logger.error(e, " Error while adding new user: {}", username);
        }

        return false;
    }

    /**
     *  Hash passwords using SHA-256.
     * @param password Plain text password.
     * @return Hashed password.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}


