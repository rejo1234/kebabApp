package com.restaurantApp.test.Order;

import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor

public class OrderMapper {
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public Order dtoToOrderForCreate(OrderDto orderDto) {
        Repository repository = repositoryRepository.findById(orderDto.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("repository nie istnieje"));
        Restaurant restaurant = restaurantRepository.findById(orderDto.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("restaurant nie istnieje"));
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user nie istnieje"));
        return Order.builder()
                .id(orderDto.getId())
                .user(user)
                .restaurant(restaurant)
                .repository(repository)
                .orderState(OrderState.PENDING)
                .orderName(orderDto.getOrderName())
                .spaceForComment(orderDto.getSpaceForComment())
                .dateOfCreate(LocalDateTime.now())
                .dateToPickUp(orderDto.getDateToPickUp())
                .orderProductDtoList(orderDto.getOrderProductDtoList())
                .build();
    }

    public List<OrderDto> ordersToDtos(List<Order> orders) {
        return orders.stream()
                .map(this::orderToDto)
                .toList();
    }

    public OrderDto orderToDto(Order order) {
        Integer repositoryId = order.getRepository().getId();
        Integer restaurantId = order.getRepository().getId();
        Integer userId = order.getRepository().getId();
        return OrderDto.builder()
                .id(order.getId())
                .userId(repositoryId)
                .restaurantId(restaurantId)
                .repositoryId(userId)
                .orderState(order.getOrderState())
                .orderName(order.getOrderName())
                .spaceForComment(order.getSpaceForComment())
                .dateOfCreate(LocalDateTime.now())
                .dateToPickUp(order.getDateToPickUp())
                .orderProductDtoList(order.getOrderProductDtoList())
                .build();
    }
}
