package com.ecommerceproject.service;

import com.ecommerceproject.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;


@Getter
@AllArgsConstructor
@Service
public class UserService {
    private List<User> users;

    public void addUser(User user) {
        users.add(user);
    }

    public String deposit(String email, double amount) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                u.setBudget(u.getBudget() + amount);
                return "Deposited successfully, new budget: " + u.getBudget();
            }
        }
        return "User not found";
    }

    public User getUserByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }
}
