package org.example.travelexpertdesktopapplication.auth;

import java.util.UUID;

public class Admin extends User {
    // new admin
    public Admin(String username, String password) {
        super(username, password, UserRole.ADMIN, null, null);
    }

    // existing admin
    public Admin(UUID id, String username, String passwordHash) {
        super(id, username, passwordHash, UserRole.ADMIN, null, null);
    }
}
