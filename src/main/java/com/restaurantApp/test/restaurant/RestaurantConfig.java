package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.user.UserRepository;
import com.restaurantApp.test.user.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {
    @Bean
    public RestaurantValidator restaurantValidator(ContextService contextService) {
        return new RestaurantValidator(contextService);
    }

    @Bean
    public RestaurantService restaurantService(
            RepositoryRepository repositoryRepository,
            RestaurantRepository restaurantRepository,
            RestaurantMapper restaurantMapper,
            RestaurantValidator restaurantValidator,
            UserValidator userValidator,
            RepositoryValidator repositoryValidator
    ) {
        return new RestaurantService(
                repositoryRepository,
                restaurantRepository,
                restaurantMapper,
                restaurantValidator,
                userValidator,
                repositoryValidator
        );
    }

    @Bean
    public RestaurantMapper restaurantMapper(UserRepository userRepository, RepositoryRepository repositoryRepository) {
        return new RestaurantMapper(userRepository, repositoryRepository);
    }
}
