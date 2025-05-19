package com.ecommerceproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionStore {
    private Role role;
    private String currentEmail;
}
