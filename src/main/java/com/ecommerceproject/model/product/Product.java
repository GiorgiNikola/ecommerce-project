package com.ecommerceproject.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    @Setter
    private int stock;
}
