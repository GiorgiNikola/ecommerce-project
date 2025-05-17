package com.ecommerceproject.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductRequest {
    private String name;
    private String description;
    private double price;
    private int stock;
}
