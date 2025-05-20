package com.restaurantApp.test.auth;

import com.restaurantApp.test.config.JwtService;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticateConfig {

    @Bean
    public AuthenticationService authenticationService(UserRepository userRepository,
                                                       PasswordEncoder passwordEncoder,
                                                       JwtService jwtService,
                                                       AuthenticationManager authenticationManager,
                                                       UserMapper userMapper) {
        return new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager, userMapper);
    }

    @Bean
    public ContextService authenticateContextService(
            UserRepository userRepository,
            RepositoryRepository repositoryRepository,
            ProductRepository productRepository) {
        return new ContextService(userRepository, repositoryRepository,  productRepository);
    }

    @Bean
    public UserMapper userMapper(RepositoryRepository repositoryRepository, RestaurantRepository restaurantRepository) {
        return new UserMapper(repositoryRepository, restaurantRepository);
    }
}
