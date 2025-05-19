package com.ecommerceproject.service;

import com.ecommerceproject.model.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private AuthorizationService authorizationService;

    public boolean checkAdmin() {
        return authorizationService.getSessionStore().getRole() == Role.ADMIN;
    }
}
