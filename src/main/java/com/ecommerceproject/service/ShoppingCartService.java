package com.ecommerceproject.service;

import com.ecommerceproject.model.order.OrderResult;
import com.ecommerceproject.model.order.OrderStatus;
import com.ecommerceproject.model.product.CartItem;
import com.ecommerceproject.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;


@Getter
@Service
public class ShoppingCartService {
    // მაპის ქი არის იუზერის იმეილი და ქი არის ისევ მაპი რომლის ქი არის პროდუქტის აიდი და ველიუ
    // არის ამ პროდუქტის რაოდენობა
    private Map<String, List<CartItem>> shoppingCarts;
    private ProductService productService;

    public ShoppingCartService(ProductService productService) {
        shoppingCarts = new HashMap<>();
        this.productService = productService;
    }

    // როცა იუზერი დარეგისტრირდება უნდა დაიქოლოს ეს ფუნქცია რომ მისთვის შეიქმნას კალათა
    public void createShoppingCart(String email) {
        shoppingCarts.put(email, new ArrayList<>());
    }

    // გადმოგვეცემა იუზერის იმეილი პროდუქტი
    public CartItem addProduct(String email, int id, int quantity) {
        Product product = productService.getProductById(id);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        } else if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Not enough stock");
        }
        List<CartItem> cart = shoppingCarts.get(email);
        for (CartItem cartItem : cart) {
            if (cartItem.getProduct().getId() == product.getId()) {
                int newQuantity = cartItem.getQuantity() + quantity;
                if (newQuantity > product.getStock()) {
                    throw new IllegalArgumentException("Not enough stock");
                }
                cart.remove(cartItem);
                CartItem updatedCartItem = new CartItem(product, newQuantity);
                cart.add(updatedCartItem);
                return updatedCartItem;
            }
        }
        CartItem cartItem = new CartItem(product, quantity);
        shoppingCarts.get(email).add(cartItem);
        return cartItem;
    }

    public void removeProduct(String email, int id) {
        List<CartItem> cart = shoppingCarts.get(email);
        boolean removed = cart.removeIf(item -> item.getProduct().getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Item not found in cart");
        }
    }

    public double getTotalPrice(String email) {
        List<CartItem> cart = shoppingCarts.get(email);
        double totalPrice = 0;
        for (CartItem cartItem : cart) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    public Collection<CartItem> getCartItems(String email) {
        return shoppingCarts.get(email);
    }
}
