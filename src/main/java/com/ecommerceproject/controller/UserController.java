package com.ecommerceproject.controller;

import com.ecommerceproject.model.order.OrderResult;
import com.ecommerceproject.model.product.CartItem;
import com.ecommerceproject.model.product.Product;
import com.ecommerceproject.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;
    ShoppingCartService shoppingCartService;
    AuthorizationService authorizationService;
    OrderService orderService;
    ProductService productService;

    @PostMapping("/add-cart-item")
    public CartItem addProductToShoppingCart(@RequestParam int productId, @RequestParam int quantity) {
        if (authorizationService.checkUser()){
            return shoppingCartService.addProduct(authorizationService.getSessionStore().getCurrentEmail(),productId, quantity);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @DeleteMapping("/delete-cart-item")
    public void deleteProductFromShoppingCart(@RequestParam int productId) {
        if (authorizationService.checkUser()){
            shoppingCartService.removeProduct(authorizationService.getSessionStore().getCurrentEmail(),productId);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @GetMapping("/get-cart-items")
    public Collection<CartItem> getCartItems() {
        if (authorizationService.checkUser()){
            return shoppingCartService.getCartItems(authorizationService.getSessionStore().getCurrentEmail());
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @PostMapping("/place-order")
    public OrderResult placeOrder() {
        if (authorizationService.checkUser()){
            return orderService.placeOrder(authorizationService.getSessionStore().getCurrentEmail());
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @GetMapping("/get-products-list")
    public Collection<Product> getProducts() {
        if (authorizationService.checkUser()){
            return productService.getAllProducts();
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @GetMapping("/get-budget")
    public String getBalance() {
        if (authorizationService.checkUser()){
            return "Budget: " +
                    userService.getUserByEmail(authorizationService.getSessionStore().getCurrentEmail()).getBudget();
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount) {
        if (authorizationService.checkUser()){
            return userService.deposit(authorizationService.getSessionStore().getCurrentEmail(), amount);
        } else {
            throw new RuntimeException("Access denied");
        }
    }
}
