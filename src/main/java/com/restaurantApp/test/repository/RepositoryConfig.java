package com.restaurantApp.test.repository;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.restaurant.RestaurantValidator;
import com.restaurantApp.test.user.UserRepository;
import com.restaurantApp.test.user.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public RepositoryValidator repositoryValidator(ContextService contextService) {
        return new RepositoryValidator(contextService);
    }

    @Bean
    public RepositoryService repositoryService(
            RepositoryRepository repositoryRepository,
            RestaurantRepository restaurantRepository,
            ProductRepository productRepository,
            RepositoryMapper repositoryMapper,
            UserRepository userRepository,
            RestaurantValidator restaurantValidator,
            UserValidator userValidator,
            RepositoryValidator repositoryValidator) {
        return new RepositoryService(repositoryRepository,
                restaurantRepository,
                productRepository,
                repositoryMapper,
                userRepository,
                restaurantValidator,
                userValidator,
                repositoryValidator);
    }

    @Bean
    public RepositoryMapper repositoryMapper(
            UserRepository userRepository,
            ProductRepository productRepository,
            RestaurantRepository restaurantRepository) {
        return new RepositoryMapper(userRepository, productRepository, restaurantRepository);
    }
}
