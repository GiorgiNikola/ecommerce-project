package com.ecommerceproject.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
}
