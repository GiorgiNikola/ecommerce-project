package com.ecommerceproject.controller;

import com.ecommerceproject.model.product.Product;
import com.ecommerceproject.model.product.ProductRequest;
import com.ecommerceproject.service.AdminService;
import com.ecommerceproject.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor
@RestController
public class AdminController {
    private ProductService productService;
    private AdminService adminService;

    @GetMapping("/admin/get-products")
    public Collection<Product> getProducts() {
        if (adminService.checkAdmin()){
            return productService.getAllProducts();
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @PostMapping("/admin/add-product")
    public Product addProduct(@RequestBody ProductRequest productRequest) {
        if (adminService.checkAdmin()){
            return productService.addProduct(productRequest);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @PutMapping("/admin/update-product/{id}")
    public Product updateProduct(@PathVariable("id") int id,@RequestBody ProductRequest productRequest) {
        if (adminService.checkAdmin()){
            return productService.updateProduct(id, productRequest);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @DeleteMapping("/admin/delete-product/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        if (adminService.checkAdmin()){
            productService.deleteProductById(id);
        } else {
            throw new RuntimeException("Access denied");
        }
    }
}
