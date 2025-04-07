package org.example.travelexpertdesktopapplication.services;

import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.auth.User;
import org.example.travelexpertdesktopapplication.dao.UserDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;


    public AuthService() {
        this.userDAO = new UserDAO();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Authenticate a user with the given username and password.
     *
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     * @return An Optional containing the authenticated User if successful, or an empty Optional otherwise.
     */
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent() && verifyPassword(password, userOpt.get().getPasswordHash())) {
            return userOpt;
        }
        return Optional.empty();
    }

    public void logout() {
        SessionManager.getInstance().clearSession();
    }

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
