package com.restaurantApp.test.product;

import com.restaurantApp.test.repository.CreateRepositoryRequest;
import com.restaurantApp.test.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product dtoToProduct(CreateProductRequest createProductRequest){
        return  Product.builder()
                .name(createProductRequest.getProductDto().getName())
                .weight(createProductRequest.getProductDto().getWeight())
                .build();
    }
}
