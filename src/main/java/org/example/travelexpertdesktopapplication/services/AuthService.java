package org.example.travelexpertdesktopapplication.services;

import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.auth.User;
import org.example.travelexpertdesktopapplication.dao.UserDAO;

import java.util.List;
import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
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
        if (userOpt.isPresent() && userOpt.get().authenticate(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

    public void logout() {
        SessionManager.getInstance().clearSession();
    }
}
