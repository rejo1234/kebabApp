package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.RepositoryMapper;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public OrderService orderService(
            RepositoryRepository repositoryRepository,
            RestaurantRepository restaurantRepository,
            UserRepository userRepository,
            ContextService contextService,
            OrderRepository orderRepository,
            OrderMapper orderMapper,
            OrderValidator orderValidator) {
        return new OrderService(contextService,
                orderRepository,
                orderMapper,
                restaurantRepository,
                repositoryRepository,
                userRepository,
                orderValidator);
    }

    @Bean
    public OrderValidator orderValidator(ContextService contextService,OrderRepository orderRepository) {
        return new OrderValidator(contextService, orderRepository);
    }

    @Bean
    public OrderMapper orderMapper(RepositoryRepository repositoryRepository,RestaurantRepository restaurantRepository,UserRepository userRepository) {
        return new OrderMapper(repositoryRepository, restaurantRepository, userRepository);
    }
}
