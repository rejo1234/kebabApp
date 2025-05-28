package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class OrderService {
    private final ContextService contextService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;


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
        orderValidator.validateCreateOrder(orderDto, userId);
        var order = orderMapper.dtoToOrderForCreate(orderDto);
        orderRepository.save(order);
    }


    public List<OrderDto> getOrdersList(SearchParam searchParam) {
        orderValidator.validateFullContext(searchParam.restaurantIds, searchParam.repositoryIds, searchParam.userId);
        List<Integer> repoIdList = CollectionUtils.isEmpty(searchParam.repositoryIds) ? contextService.getUserRepositoryIds() : searchParam.repositoryIds;
        List<Integer> restaurantIdList = CollectionUtils.isEmpty(searchParam.restaurantIds) ? contextService.getUserRestaurantIds() : searchParam.restaurantIds;
        List<Order> orderList = orderRepository.findAll(OrderFilter.buildFilter(searchParam, restaurantIdList, repoIdList));
        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    public List<OrderDto> getOrdersList2(SearchParam2 searchParam2, Integer userId) {
        orderValidator.validateFullContext(searchParam2.getGetOrderListRequest().getRepositoryIdList(), searchParam2.getGetOrderListRequest().getRepositoryIdList(), userId);
        List<Integer> repoIdList = CollectionUtils.isEmpty(searchParam2.getGetOrderListRequest().getRepositoryIdList()) ? contextService.getUserRepositoryIds() : searchParam2.getGetOrderListRequest().getRepositoryIdList();
        List<Integer> restaurantIdList = CollectionUtils.isEmpty(searchParam2.getGetOrderListRequest().getRestaurantIdList()) ? contextService.getUserRestaurantIds() : searchParam2.getGetOrderListRequest().getRestaurantIdList();
        List<Order> orderList = orderRepository.getOrderListRequest2(searchParam2, repoIdList, restaurantIdList, userId);

        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

}
