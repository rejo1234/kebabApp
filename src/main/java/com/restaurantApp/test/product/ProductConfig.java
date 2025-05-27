package com.restaurantApp.test.product;

import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.user.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {
    @Bean
    public ProductValidator productValidator(
            UserValidator userValidator,
            RepositoryValidator repositoryValidator,
            ProductRepository productRepository) {
        return new ProductValidator(userValidator, repositoryValidator, productRepository);
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }

    @Bean
    public ProductService productService(
            ProductRepository productRepository,
            ProductMapper productMapper,
            RepositoryRepository repositoryRepository,
            UserValidator userValidator,
            RepositoryValidator repositoryValidator,
            ProductValidator productValidator) {

        return new ProductService(productRepository,
                productMapper,
                repositoryRepository,
                userValidator,
                repositoryValidator,
                productValidator
        );
    }
}
