package com.ecommerceproject.service;

import com.ecommerceproject.model.product.CartItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private ShoppingCartService shoppingCartService;

    public boolean placeOrder(String email) {
        List<CartItem> userCart = shoppingCartService.getShoppingCarts().get(email);
        if (userCart.isEmpty()) {

        }
        return false;
    }
}
