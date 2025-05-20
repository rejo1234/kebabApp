package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@AllArgsConstructor
public class OrderService {
    private final ContextService contextService;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    RestaurantRepository restaurantRepository;
    RepositoryRepository repositoryRepository;
    UserRepository userRepository;
    OrderValidator orderValidator;
    public void cancelOrder(Integer orderId, Integer userId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order nie istnieje"));
        orderValidator.validateOrderContext(order, userId);
        order.setOrderState(OrderState.CANCELLED);
        orderRepository.save(order);
    }
    public void deleteOrder(Integer orderId, Integer userId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order nie istnieje"));
        orderValidator.validateOrderContext(order, userId);
        orderRepository.delete(order);
    }

    public void modifyOrder(OrderDto orderDto, Integer userId) {
        var order = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("order nie istnieje"));
        orderValidator.validateOrderContext(order, userId);
            order = orderMapper.dtoToOrderForCreate(orderDto);
            orderRepository.save(order);
    }

    public void createOrder(OrderDto orderDto, Integer userId) {
        orderValidator.ValidateCreateOrder(orderDto, userId);
        var order = orderMapper.dtoToOrderForCreate(orderDto);
        orderRepository.save(order);
    }


    public List<OrderDto> getOrdersList(List<Integer> restaurantIds, List<Integer> repositoryIds, Integer userId) {
        orderValidator.ValidateGetOrdersList(restaurantIds, repositoryIds, userId);
        // to tez do validatora? imo tak
        List<Integer> repoIdList = CollectionUtils.isEmpty(repositoryIds) ? contextService.getUserRepositoryIds() : repositoryIds;
        List<Integer> restaurantIdList = CollectionUtils.isEmpty(restaurantIds) ? contextService.getUserRestaurantIds() : restaurantIds;
        List<Order> orderList = orderRepository.findAllByRestaurantIdInAndRepositoryIdInAndUserId(repoIdList, restaurantIdList, userId);

        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }
}
