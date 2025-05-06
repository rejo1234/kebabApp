package com.restaurantApp.test.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor

public class ShowProductResponse {
    private final ProductService productService;

    public List<Product> getListProducts(Integer userId) {
        return productService.availableProducts(userId);
    }

    public Product getProduct(String product, Integer userId) {
        return productService.findProduct(product, userId);
    }
}
