package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final ContextService contextService;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    RestaurantRepository restaurantRepository;
    RepositoryRepository repositoryRepository;
    UserRepository userRepository;
    OrderValidator orderValidator;

    public void cancelOrder(OrderDto orderDto, Integer userId) {
        orderValidator.modifyCancelOrder(orderDto, userId);
        // pytanie do order w swaggerze trzeba idusera bo do mappera idzie a walidacje robie po userId?
        var order = orderMapper.dtoToOrder(orderDto);
        orderRepository.delete(order);
    }

    public void modifyOrder(OrderDto orderDto, Integer userId) {
        orderValidator.modifyOrderValidator(orderDto, userId);
        //To tez do walidacji? imo nie bo robimy tutaj save
        orderDto.setOrderState(OrderState.CANCELLED);
        if (orderDto.getOrderState() == OrderState.CANCELLED) {
            var order = orderMapper.dtoToOrder(orderDto);
            orderRepository.save(order);
        }
    }

    public void createOrder(OrderDto orderDto, Integer userId) {
        orderValidator.createOrderValidator(orderDto, userId);
        var order = orderMapper.dtoToOrder(orderDto);
        orderRepository.save(order);
    }


    public List<OrderDto> getOrdersList(List<Integer> restaurantIds, List<Integer> repositoryIds, Integer userId) {
        orderValidator.getOrdersListValidator(restaurantIds, repositoryIds, userId);
        // to tez do validatora? imo tak
        List<Integer> repoIdList = CollectionUtils.isEmpty(repositoryIds) ? contextService.getUserRepositoryIds() : repositoryIds;
        List<Integer> restaurantIdList = CollectionUtils.isEmpty(restaurantIds) ? contextService.getUserRestaurantIds() : restaurantIds;
        List<Order> orderList = orderRepository.findAllByRestaurantIdInAndRepositoryIdInAndUserId(repoIdList, restaurantIdList, userId);

        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }
}
