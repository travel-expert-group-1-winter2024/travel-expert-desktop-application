package org.example.travelexpertdesktopapplication.services;

import org.example.travelexpertdesktopapplication.auth.User;
import org.example.travelexpertdesktopapplication.dao.UserDAO;

import java.util.List;
import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().authenticate(password)) {
            return userOpt;
        }
        return Optional.empty();
    }
}
