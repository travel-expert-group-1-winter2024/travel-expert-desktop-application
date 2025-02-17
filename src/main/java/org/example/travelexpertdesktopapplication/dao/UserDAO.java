package org.example.travelexpertdesktopapplication.dao;

import org.example.travelexpertdesktopapplication.auth.Admin;
import org.example.travelexpertdesktopapplication.auth.Agent;
import org.example.travelexpertdesktopapplication.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new Admin("Admin", "", "Admin", "admin@example", "123-456-7890", "admin", "Admin@1234"));
        users.add(new Agent("Agent", "", "Agent", "agent@example.com", "123-456-7890", "agent", "Agent@1234"));
        users.add(new Agent("Manager", "", "Manager", "manager@example.com", "123-456-7890", "manager", "Manager@1234"));
    }

    public Optional<User> findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
