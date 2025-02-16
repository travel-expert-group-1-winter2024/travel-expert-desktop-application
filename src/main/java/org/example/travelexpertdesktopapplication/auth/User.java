package org.example.travelexpertdesktopapplication.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public abstract class User {
    protected UUID id;
    protected String firstName;
    protected String middleInitial;
    protected String lastName;
    protected String email;
    protected String phone;
    protected String username;
    protected String passwordHash;
    protected UserRole role;

    protected User(String firstName, String middleInitial, String lastName, String email, String phone, String username, String password, UserRole role) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

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

    public boolean authenticate(String password) {
        return hashPassword(password).equals(passwordHash);
    }

    public UserRole getRole(){
        return role;
    }

    public void logout() {
        System.out.println("User logged out");
        //TODO: might need to implement more logic here
    }

}
