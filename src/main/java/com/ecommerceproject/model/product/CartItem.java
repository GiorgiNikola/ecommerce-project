package com.ecommerceproject.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItem {
    private Product product;
    private int quantity;
}
