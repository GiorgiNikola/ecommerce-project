package com.ecommerceproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationRequest {
    private String email;
    private String username;
    private String password;
}
