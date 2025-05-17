package com.ecommerceproject.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderResult {
    private OrderStatus status;
    private String message;
    private double totalPrice;
}
