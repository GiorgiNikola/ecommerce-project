package com.ecommerceproject.controller;

import com.ecommerceproject.model.user.RegistrationRequest;
import com.ecommerceproject.service.AuthorizationService;
import com.ecommerceproject.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private AuthorizationService authorizationService;
    private ShoppingCartService shoppingCartService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequest rq) {
        return authorizationService.registerUser(rq);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,@RequestParam String password) {
        if (authorizationService.login(username, password)) {
            return "Successfully logged in";
        } else {
            return "Invalid username or password";
        }
    }


    @PostMapping("/logout")
    public String logout() {
        if (authorizationService.logout()) {
            return "Successfully logged out";
        } else {
            return "you are not logged in";
        }
    }
}
