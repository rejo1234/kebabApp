package com.restaurantApp.test.Order;

import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.restaurant.RestaurantValidator;
import com.restaurantApp.test.user.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
public class OrderValidator {
    private final RepositoryValidator repositoryValidator;
    private final RestaurantValidator restaurantValidator;
    private final UserValidator userValidator;

    public void validateOrderContext(Order order, Integer userId){
        validateOrderStateIsOperable(order);
        userValidator.validateUserId(userId);
        repositoryValidator.validateRepositoryId(order.getRepository().getId());
        restaurantValidator.validateRestaurantId(order.getRestaurant().getId());
    }
    public void validateFullContext(List<Integer> restaurantIds, List<Integer> repositoryIds, Integer userId){
        userValidator.validateUserId(userId);
        restaurantValidator.validateRestaurantIdList(restaurantIds);
        repositoryValidator.validateRepositoryIdList(repositoryIds);
    }
    public void validateCreateOrder(OrderDto orderDto, Integer userId){
        validateTime(orderDto.getDateToPickUp());
        userValidator.validateUserId(userId);
        repositoryValidator.validateRepositoryId(orderDto.getRepositoryId());
        restaurantValidator.validateRestaurantId(orderDto.getRestaurantId());
    }
    public void validateTime(LocalDateTime timeToPickUp){
        if (LocalDateTime.now().isBefore(timeToPickUp)){
            throw new AccessDeniedException("Godzina jest bledna");
        }
    }
    public void validateOrderStateIsOperable(Order order) {
        if (order.getOrderState() == OrderState.APPROVED || order.getOrderState() == OrderState.REJECTED) {
            System.out.println("Nie mozesz zmodyfikować zamowienia");
            throw new AccessDeniedException("status jest bledny");
        }
    }
}
