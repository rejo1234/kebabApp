package com.restaurantApp.test.repository;

import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RepositoryConfig {
    UserRepository userRepository;
    ProductRepository productRepository;
    RestaurantRepository restaurantRepository;

    @Bean
    public RepositoryMapper repositoryMapper() {
        return new RepositoryMapper(userRepository, productRepository, restaurantRepository);
    }
}
