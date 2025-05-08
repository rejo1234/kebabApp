package com.restaurantApp.test.Order;

import com.restaurantApp.test.repository.RepositoryMapper;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class OrderConfig {
    RepositoryRepository repositoryRepository;
    RestaurantRepository restaurantRepository;
    UserRepository userRepository;
    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper(repositoryRepository, restaurantRepository, userRepository);
    }
}
