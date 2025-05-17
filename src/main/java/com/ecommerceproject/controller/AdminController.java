package com.ecommerceproject.controller;

import com.ecommerceproject.model.product.Product;
import com.ecommerceproject.model.product.ProductRequest;
import com.ecommerceproject.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor
@RestController
public class AdminController {
    private ProductService productService;

    @GetMapping("/admin/get-products")
    public Collection<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/admin/add-product")
    public Product addProduct(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @PutMapping("/admin/update-product/{id}")
    public Product updateProduct(@PathVariable("id") int id,@RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/admin/delete-product/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        productService.deleteProductById(id);
    }
}
