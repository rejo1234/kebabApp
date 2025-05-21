package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.UserMapper;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.restaurant.RestaurantValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }

    @Bean
    public UserService userService(RestaurantRepository restaurantRepository,
                                   UserRepository userRepository,
                                   RepositoryRepository repositoryRepository,
                                   UserMapper userMapper,
                                   RestaurantValidator restaurantValidator,
                                   UserValidator userValidator,
                                   RepositoryValidator repositoryValidator) {

        return new UserService(
                restaurantRepository,
                userRepository,
                repositoryRepository,
                userMapper,
                restaurantValidator,
                userValidator,
                repositoryValidator);
    }
}
