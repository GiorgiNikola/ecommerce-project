package com.ecommerceproject.service;

import com.ecommerceproject.model.user.RegistrationRequest;
import com.ecommerceproject.model.user.Role;
import com.ecommerceproject.model.user.SessionStore;
import com.ecommerceproject.model.user.User;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private UserService userService;
    @Getter
    private SessionStore sessionStore;
    private ShoppingCartService shoppingCartService;
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";

    public AuthorizationService(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        sessionStore = new SessionStore();
    }

    public String registerUser(RegistrationRequest rq) {
        for (User u : userService.getUsers()) {
            if (u.getUsername().equals(rq.getUsername()) || u.getEmail().equals(rq.getEmail())) {
                return "User already exists";
            }
        }
        User newUser = new User(rq.getUsername(), rq.getPassword(), rq.getEmail(), 1000);
        userService.addUser(newUser);
        shoppingCartService.createShoppingCart(rq.getEmail());
        return "User successfully registered";
    }

    public boolean login(String username, String password) {
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            sessionStore.setRole(Role.ADMIN);
            return true;
        }
        User loggedUser = null;
        for (User u : userService.getUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                loggedUser = u;
            }
        }
        if (loggedUser != null) {
            sessionStore.setRole(Role.USER);
            sessionStore.setCurrentEmail(loggedUser.getEmail());
        }
        return loggedUser != null;
    }

    public boolean logout() {
        if (sessionStore.getRole() != null) {
            sessionStore.setRole(null);
            sessionStore.setCurrentEmail(null);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUser() {
        return sessionStore.getRole() == Role.USER;
    }
}
