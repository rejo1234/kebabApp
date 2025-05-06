package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RestaurantConfig {
    UserRepository userRepository;
    RepositoryRepository repositoryRepository;

    @Bean
    public RestaurantMapper restaurantMapper() {
        return new RestaurantMapper(userRepository, repositoryRepository);
    }
}
