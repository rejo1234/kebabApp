package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.repository.RepositoryMapper;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryService;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    public RestaurantService restaurantService(
            RepositoryRepository repositoryRepository,
            RestaurantRepository restaurantRepository,
            RestaurantMapper restaurantMapper,
            ContextService authenticationContextService
    ) {
        return new RestaurantService(
                repositoryRepository,
                restaurantRepository,
                restaurantMapper,
                authenticationContextService
        );
    }
    @Bean
    public RestaurantMapper restaurantMapper(UserRepository userRepository,RepositoryRepository repositoryRepository) {
        return new RestaurantMapper(userRepository, repositoryRepository);
    }
}
