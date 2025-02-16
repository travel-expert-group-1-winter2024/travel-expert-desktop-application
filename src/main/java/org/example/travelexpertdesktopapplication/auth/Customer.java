package org.example.travelexpertdesktopapplication.auth;

public class Customer extends User {

    protected Customer(String firstName, String middleInitial, String lastName, String email, String phone, String username, String password) {
        super(firstName, middleInitial, lastName, email, phone, username, password, UserRole.CUSTOMER);
    }
}
