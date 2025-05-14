package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@AllArgsConstructor
public class OrderValidator {
    ContextService contextService;
    OrderRepository orderRepository;
    public void modifyCancelOrder(OrderDto orderDto, Integer userId){
        if (!orderRepository.existsById(orderDto.getId())) {
            throw new IllegalArgumentException("Order nie istnieje");
        }
        contextService.validateDeleteOrder(orderDto);
        contextService.validateUserId(userId);
        contextService.validateRepositoryId(orderDto.getRepositoryId());
        contextService.validateRestaurantId(orderDto.getRestaurantId());
    }
    public void modifyOrderValidator(OrderDto orderDto, Integer userId){
        if (!orderRepository.existsById(orderDto.getId())) {
            throw new IllegalArgumentException("Order nie istnieje");
        }
        contextService.validateUserId(userId);
        contextService.validateOrderStatus(orderDto);
        contextService.validateRepositoryId(orderDto.getRepositoryId());
    }
    public void getOrdersListValidator(List<Integer> restaurantIds, List<Integer> repositoryIds, Integer userId){
        contextService.validateUserId(userId);
        contextService.validateRestaurantIdList(restaurantIds);
        contextService.validateRepositoryIdList(repositoryIds);
    }
    public void createOrderValidator(OrderDto orderDto, Integer userId){
        if (!userId.equals(orderDto.getUserId())) {
            throw new IllegalArgumentException("User Id is not equals");
        }
        contextService.validateTime(orderDto.getDateToPickUp());
        contextService.validateUserId(userId);
        contextService.validateRepositoryId(orderDto.getRepositoryId());
        contextService.validateRestaurantId(orderDto.getRestaurantId());
    }
}
