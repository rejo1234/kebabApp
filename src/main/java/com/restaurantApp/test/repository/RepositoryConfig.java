package com.restaurantApp.test.repository;

import com.restaurantApp.test.Order.OrderMapper;
import com.restaurantApp.test.Order.OrderRepository;
import com.restaurantApp.test.Order.OrderService;
import com.restaurantApp.test.Order.OrderValidator;
import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public RepositoryService repositoryService(
            RepositoryRepository repositoryRepository,
            RestaurantRepository restaurantRepository,
            ProductRepository productRepository,
            RepositoryMapper repositoryMapper,
            ContextService authenticationContextService,
            UserRepository userRepository) {
        return new RepositoryService(repositoryRepository,
                restaurantRepository,
                productRepository,
                repositoryMapper,
                authenticationContextService,
                userRepository);
    }
    @Bean
    public RepositoryMapper repositoryMapper(
            UserRepository userRepository,
            ProductRepository productRepository,
            RestaurantRepository restaurantRepository) {
        return new RepositoryMapper(userRepository, productRepository, restaurantRepository);
    }
}
