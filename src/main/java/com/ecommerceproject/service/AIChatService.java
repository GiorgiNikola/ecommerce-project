package com.ecommerceproject.service;

import com.google.genai.Chat;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AIChatService {
    private final Client client;
    private final Chat chatSession;
    private List<Content> history;

    private final ProductService productService;

    public AIChatService(ProductService productService) {
        this.productService = productService;
        client = new Client();
        chatSession = client.chats.create("gemini-2.0-flash-001");
    }

   public String sendMessage(String message) {
       String productList = productService.getProductListAsString();

       String prompt = """
        You are a helpful shopping assistant.
        Here is a list of products available:

        %s

        Now, help the user based on their message below.

        User: %s
        """.formatted(productList, message);

       GenerateContentResponse response =
                chatSession.sendMessage(prompt);
        history = chatSession.getHistory(true);
        return response.text();
   }

}
