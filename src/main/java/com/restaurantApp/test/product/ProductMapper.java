package com.restaurantApp.test.product;

import com.restaurantApp.test.repository.CreateRepositoryRequest;
import com.restaurantApp.test.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product dtoToProduct(CreateProductRequest createProductRequest){
        return  Product.builder()
                .nameProduct(createProductRequest.getProductDto().getNameProduct())
                .kgs(createProductRequest.getProductDto().getWeightProduct())
                .build();
    }
}
