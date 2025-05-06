package com.restaurantApp.test.product;

import com.restaurantApp.test.repository.RepositoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ProductResponseConfig {

    @Bean
    public ShowProductResponse showProductResponse(ProductService productService) {
        return new ShowProductResponse(productService);
    }

    @Bean
    public ProductMapper productMapper(RepositoryRepository repositoryRepository) {
        return new ProductMapper(repositoryRepository);
    }
}
