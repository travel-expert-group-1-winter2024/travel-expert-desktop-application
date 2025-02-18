package org.example.travelexpertdesktopapplication.auth;

import java.util.UUID;

public class Agent extends User {
    // new agent
    public Agent(String username, String password) {
        super(username, password, UserRole.AGENT, null, null);
    }

    public Agent(UUID id, String username, String passwordHash, Integer agentId) {
        super(id, username, passwordHash, UserRole.AGENT, agentId, null);
    }
}
