package com.ecommerceproject.controller;

import com.ecommerceproject.service.AIChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/assistant")
public class ShoppingAssistantController {
    private final AIChatService chatService;

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatService.sendMessage(message);
    }
}
