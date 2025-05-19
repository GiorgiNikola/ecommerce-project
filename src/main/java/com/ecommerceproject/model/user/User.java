package com.ecommerceproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class User {
    private String username;
    private String password;
    private String email;
    @Setter
    private double budget = 1000;
}
