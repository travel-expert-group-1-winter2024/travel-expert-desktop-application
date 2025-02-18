package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.auth.*;

import java.sql.*;
import java.util.UUID;
import java.util.Optional;

public class UserDAO {

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

    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().authenticate(password)) {
            return userOpt;
        }
        return Optional.empty();
    }
}
