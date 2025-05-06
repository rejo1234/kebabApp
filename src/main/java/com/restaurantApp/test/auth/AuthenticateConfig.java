package com.restaurantApp.test.auth;

import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AuthenticateConfig {
    UserRepository userRepository;
    RepositoryRepository repositoryRepository;
    RestaurantRepository restaurantRepository;

    @Bean
    public AuthenticateContextService authenticateContextService() {
        return new AuthenticateContextService(userRepository, repositoryRepository);
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper(repositoryRepository, restaurantRepository);
    }
}
