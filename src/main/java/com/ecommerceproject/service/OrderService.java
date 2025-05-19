package com.ecommerceproject.service;

import com.ecommerceproject.model.order.OrderStatus;
import com.ecommerceproject.model.product.CartItem;
import com.ecommerceproject.model.order.OrderResult;
import com.ecommerceproject.model.product.Product;
import com.ecommerceproject.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private ShoppingCartService shoppingCartService;
    private ProductService productService;
    private UserService userService;

    public OrderResult placeOrder(String email) {
        List<CartItem> userCart = shoppingCartService.getShoppingCarts().get(email);
        if (userCart.isEmpty()) {
            return new OrderResult(OrderStatus.FAILED, "Shopping cart is empty", 0);
        }

        double totalPrice = shoppingCartService.getTotalPrice(email);
        OrderResult stockCheck = checkStocks(email);
        if (stockCheck.getStatus() == OrderStatus.FAILED) {
            return stockCheck;
        }
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new OrderResult(OrderStatus.FAILED, "User not found", 0);
        }

        // აქ საჭიროა შემოწმდეს იუზერი ბიუჯეტი მეტია ან ტოლია თუ არა მთლიან ფასზე
        if (user.getBudget() < totalPrice) {
            return new OrderResult(OrderStatus.FAILED, "Not enough budget", 0);
        }

        // შევამციროთ პროდუქტის მარაგები
        productService.deductStock(userCart);

        // შევამციროთ იუზერის ბიუჯეტი
        user.setBudget(user.getBudget() - totalPrice);

        // გავასუფთავოთ კალათა
        shoppingCartService.createShoppingCart(email);

        return new OrderResult(OrderStatus.SUCCESS, "Order successfully placed", totalPrice);
    }

    public OrderResult checkStocks(String email) {
        List<CartItem> cart = shoppingCartService.getShoppingCarts().get(email);
        for (CartItem cartItem : cart) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            if (quantity > product.getStock()) {
                return new OrderResult(OrderStatus.FAILED, "Not enough stock for product: " + product.getName(), 0);
            }
        }
        double totalPrice = shoppingCartService.getTotalPrice(email);
        return new OrderResult(OrderStatus.SUCCESS, "All products are in stock", totalPrice);
    }
}
