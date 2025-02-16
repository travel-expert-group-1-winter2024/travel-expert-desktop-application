package org.example.travelexpertdesktopapplication.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

abstract class User {
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

    protected boolean checkPassword(String password) {
        return hashPassword(password).equals(passwordHash);
    }

    protected void changePassword(String newPassword) {
        passwordHash = hashPassword(newPassword);
    }

    protected void changeEmail(String newEmail) {
        email = newEmail;
    }

    protected void changePhone(String newPhone) {
        phone = newPhone;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && checkPassword(password);
    }

    public void logout() {
        System.out.println("User logged out");
        //TODO: might need to implement more logic here
    }

   public UserRole getRole(){
        return role;
   }
}
