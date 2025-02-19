package org.example.travelexpertdesktopapplication.auth;

import java.util.UUID;

public class AgentManager extends Agent {
    // new manager
    public AgentManager(String username, String password) {
        super(username, password);
        this.role = UserRole.MANAGER; // Override the role
    }
    // existing manager
    public AgentManager(UUID id, String username, String passwordHash, Integer agentId) {
        super(id, username, passwordHash, agentId);
        this.role = UserRole.MANAGER; // Override the role
    }
}
