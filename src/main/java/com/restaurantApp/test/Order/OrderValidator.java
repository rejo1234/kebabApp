package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.Repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@AllArgsConstructor
public class OrderValidator {
    ContextService contextService;
    OrderRepository orderRepository;
    public void validateOrderContext(Order order, Integer userId){
        contextService.validateUserId(userId);
        contextService.validateOrderStateIsOperable(order);
        contextService.validateRepositoryId(order.getRepository().getId());
        contextService.validateRestaurantId(order.getRestaurant().getId());
    }
    public void ValidateGetOrdersList(List<Integer> restaurantIds, List<Integer> repositoryIds, Integer userId){
        contextService.validateUserId(userId);
        contextService.validateRestaurantIdList(restaurantIds);
        contextService.validateRepositoryIdList(repositoryIds);
    }
    public void ValidateCreateOrder(OrderDto orderDto, Integer userId){
        contextService.validateTime(orderDto.getDateToPickUp());
        contextService.validateUserId(userId);
        contextService.validateRepositoryId(orderDto.getRepositoryId());
        contextService.validateRestaurantId(orderDto.getRestaurantId());
    }
}
