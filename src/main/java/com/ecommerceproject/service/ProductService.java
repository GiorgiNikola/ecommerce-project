package com.ecommerceproject.service;

import com.ecommerceproject.model.product.CartItem;
import com.ecommerceproject.model.product.Product;
import com.ecommerceproject.model.product.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    Map<Integer,Product> products;
    private static int idCounter = 1;

    public ProductService() {
        products = new HashMap<>();
    }

    public Product addProduct(ProductRequest productRequest) {
        int newId = idCounter++;
        Product product = createProduct(newId, productRequest);
        products.put(newId, product);
        return product;
    }

    public Product getProductById(int id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
        return products.get(id);
    }

    public void deleteProductById(int id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        products.remove(id);
    }

    public Product updateProduct(int id,ProductRequest productRequest) {
        Product updatedProduct = createProduct(id, productRequest);
        products.put(id, updatedProduct);
        return updatedProduct;
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Product createProduct(int id, ProductRequest productRequest) {
        return new Product(id,
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getStock());
    }

    public void deductStock(List<CartItem> cart) {
        for (CartItem cartItem : cart) {
            Product product = cartItem.getProduct();
            int newStock = product.getStock() - cartItem.getQuantity();
            if (newStock == 0){
                deleteProductById(product.getId());
            }
            products.get(product.getId()).setStock(newStock);
        }
    }
}
