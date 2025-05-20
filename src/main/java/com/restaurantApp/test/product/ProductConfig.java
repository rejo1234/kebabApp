package com.restaurantApp.test.product;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.RepositoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public ProductMapper productMapper(RepositoryRepository repositoryRepository) {
        return new ProductMapper(repositoryRepository);
    }

    @Bean
    public ProductService productService(final ProductRepository productRepository,
                                         final ProductMapper productMapper,
                                         final RepositoryRepository repositoryRepository,
                                         final ContextService authenticationContextService) {
        return new ProductService(productRepository,
                productMapper,
                repositoryRepository,
                authenticationContextService
        );
    }
}
