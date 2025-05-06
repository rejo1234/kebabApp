package com.restaurantApp.test.DatesAtStart;

import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class DateAtStartConfig {
    UserRepository userRepository;
    RestaurantRepository restaurantRepository;
    RepositoryRepository repositoryRepository;
    ProductRepository productRepository;
    PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createDatesAtStartRunner() {
        return new CreateDatesAtStartApplication(
                userRepository,
                restaurantRepository,
                repositoryRepository,
                productRepository,
                passwordEncoder
        );
    }
}
