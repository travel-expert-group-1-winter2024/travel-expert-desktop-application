package org.example.travelexpertdesktopapplication.auth;

public class AgentManager extends User {

    protected AgentManager(String firstName, String middleInitial, String lastName, String email, String phone, String username, String password) {
        super(firstName, middleInitial, lastName, email, phone, username, password, UserRole.MANAGER);
    }
}
