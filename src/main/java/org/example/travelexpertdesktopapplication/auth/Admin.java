package org.example.travelexpertdesktopapplication.auth;

public class Admin extends User {

    protected Admin(String firstName, String middleInitial, String lastName, String email, String phone, String username, String password, UserRole role) {
        super(firstName, middleInitial, lastName, email, phone, username, password, UserRole.ADMIN);
    }
}
