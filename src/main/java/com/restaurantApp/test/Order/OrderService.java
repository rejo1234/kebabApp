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
    private final OrderFilter orderFilter;


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


    public List<OrderDto> getOrdersList(
            List<Integer> restaurantIds,
            List<Integer> repositoryIds,
            OrderState orderState,
            LocalDate createTimeStart,
            LocalDate createTimeEnd,
            LocalDate dateToPickUpStart,
            LocalDate dateToPickUpEnd,
            String orderName,
            String spaceForComment,
            String productName,
            Integer productAmount,
            Integer userId) {
        orderValidator.validateFullContext(restaurantIds, repositoryIds, userId);
        List<Integer> repoIdList = CollectionUtils.isEmpty(repositoryIds) ? contextService.getUserRepositoryIds() : repositoryIds;
        List<Integer> restaurantIdList = CollectionUtils.isEmpty(restaurantIds) ? contextService.getUserRestaurantIds() : restaurantIds;
        List<Order> orderList = orderRepository.findAll(orderFilter.buildFilter(
                restaurantIdList,
                repoIdList,
                orderState,
                createTimeStart,
                createTimeEnd,
                dateToPickUpStart,
                dateToPickUpEnd,
                orderName,
                spaceForComment,
                productName,
                productAmount,
                userId));
        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    public List<OrderDto> getOrdersList2(SearchParam searchParam, Integer userId) {
        orderValidator.validateFullContext(searchParam.getGetOrderListRequest().getRepositoryIdList(), searchParam.getGetOrderListRequest().getRepositoryIdList(), userId);
        List<Integer> repoIdList = CollectionUtils.isEmpty(searchParam.getGetOrderListRequest().getRepositoryIdList()) ? contextService.getUserRepositoryIds() : searchParam.getGetOrderListRequest().getRepositoryIdList();
        List<Integer> restaurantIdList = CollectionUtils.isEmpty(searchParam.getGetOrderListRequest().getRestaurantIdList()) ? contextService.getUserRestaurantIds() : searchParam.getGetOrderListRequest().getRestaurantIdList();
        List<Order> orderList = orderRepository.getOrderListRequest2(searchParam, repoIdList, restaurantIdList, userId);

        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

}
