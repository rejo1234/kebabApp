package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.restaurant.RestaurantValidator;
import com.restaurantApp.test.user.UserRepository;
import com.restaurantApp.test.user.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public OrderFilter orderFilter() {
        return new OrderFilter();
    }

    @Bean
    public OrderValidator orderValidator(
            RepositoryValidator repositoryValidator,
            RestaurantValidator restaurantValidator,
            UserValidator userValidator) {
        return new OrderValidator(repositoryValidator, restaurantValidator, userValidator);
    }

    @Bean
    public OrderService orderService(
            ContextService contextService,
            OrderRepository orderRepository,
            OrderMapper orderMapper,
            OrderValidator orderValidator,
            OrderFilter orderFilter) {
        return new OrderService(contextService,
                orderRepository,
                orderMapper,
                orderValidator,
                orderFilter);
    }


    @Bean
    public OrderMapper orderMapper(RepositoryRepository repositoryRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        return new OrderMapper(repositoryRepository, restaurantRepository, userRepository);
    }
}
