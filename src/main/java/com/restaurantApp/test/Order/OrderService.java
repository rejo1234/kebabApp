package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.AuthenticateContextService;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryDto;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthenticateContextService authenticationContextService;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    RestaurantRepository restaurantRepository;
    RepositoryRepository repositoryRepository;
    UserRepository userRepository;

    public void makeOrder(CreateOrderRequest createOrderRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        var order = orderMapper.dtoToOrder(createOrderRequest.getOrderDto());
        orderRepository.save(order);
    }

    public List<OrderDto> getAllOrders(Integer userId) {
        authenticationContextService.validateUserId(userId);
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    public List<OrderDto> getOrdersForRestaurant(Integer restaurantId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        // authenticationContextService.validateRestaurantList(restaurantId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        return restaurant.getOrderList().stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    public List<OrderDto> getOrdersForRepository(Integer repositoryId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        //  authenticationContextService.validateRepositoryList(repositoryId);
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        return repository.getOrderList().stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    public List<OrderDto> getOrdersForUser(Integer userId) {
        authenticationContextService.validateUserId(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        return user.getOrderList().stream()
                .map(orderMapper::orderToDto)
                .toList();
    }
}
