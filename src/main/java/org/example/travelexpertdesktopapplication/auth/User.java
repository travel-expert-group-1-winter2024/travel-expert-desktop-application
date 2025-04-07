package org.example.travelexpertdesktopapplication.auth;

import org.example.travelexpertdesktopapplication.services.AuthService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public abstract class User {
    protected UUID id;
    protected String username;
    protected String passwordHash;
    protected UserRole role;
    protected Integer agentId;  // Nullable (only for Agents)
    protected Integer customerId;  // Nullable (only for Customers)

    // this is for new user
    protected User(String username, String plainPassword, UserRole role, Integer agentId, Integer customerId) {
        this.id = UUID.randomUUID();
        this.username = username;

        AuthService authService = new AuthService();
        this.passwordHash = authService.hashPassword(plainPassword);

        this.role = role;
        this.agentId = agentId;
        this.customerId = customerId;
    }

    // this is for existing user
    protected User(UUID id, String username, String hashedPassword, UserRole role, Integer agentId, Integer customerId) {
        this.id = id;
        this.username = username;
        this.passwordHash = hashedPassword;
        this.role = role;
        this.agentId = agentId;
        this.customerId = customerId;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

}
