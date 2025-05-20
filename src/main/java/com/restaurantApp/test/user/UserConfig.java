package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.auth.UserMapper;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    UserService userService(RestaurantRepository restaurantRepository,
                            UserRepository userRepository,
                            RepositoryRepository repositoryRepository,
                            ContextService authenticationContextService,
                            UserMapper userMapper) {
        return new UserService(restaurantRepository, userRepository, repositoryRepository, authenticationContextService, userMapper);
    }
}
