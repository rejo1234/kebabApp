package com.restaurantApp.test.Order;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.repository.RepositoryProductRequest;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Service
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


    public List<OrderDto> getOrdersList(List<Integer> restaurantIds, List<Integer> repositoryIds, Integer userId, LocalDateTime start, LocalDateTime end, OrderState orderState, LocalDateTime pickUpDate, String orderName, String comment) {
        orderValidator.getOrdersListValidator(restaurantIds, repositoryIds, userId);
        List<Order> orderList;

        // to tez do validatora? imo tak
        List<Integer> repoIdList = CollectionUtils.isEmpty(repositoryIds) ? contextService.getUserRepositoryIds() : repositoryIds;
        List<Integer> restaurantIdList = CollectionUtils.isEmpty(restaurantIds) ? contextService.getUserRestaurantIds() : restaurantIds;
        if (orderState != null) {
            orderList = orderRepository.findAllByOrderState(orderState);
        } else if (!comment.isEmpty()) {
            orderList = orderRepository.findAllBySpaceForCommentContainingIgnoreCase(comment);
        } else if (!orderName.isEmpty()){
         orderList = orderRepository.findAllByOrderNameContainingIgnoreCase(orderName);
        } //else if (pickUpDate != null) {
          //  orderList = orderRepository.findAllByOrderByDateToPickUpAsc(pickUpDate);
        //}
        else if ((start == null && end == null)) {
            orderList = orderRepository.findAllByRestaurantIdInAndRepositoryIdInAndUserId(repoIdList, restaurantIdList, userId);
        } else {
            orderList = orderRepository.findAllByRestaurantIdInAndRepositoryIdInAndUserIdAndDateOfCreateBetween(repoIdList, restaurantIdList, userId, start, end);
        }
        return orderList.stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    public void declineOrder(OrderDto orderDto, Integer userId) {
        orderValidator.declineOrderValidator(orderDto, userId);
        var order = orderMapper.dtoToOrder(orderDto);
        order.setOrderState(OrderState.CANCELLED);
        orderRepository.save(order);
    }

    public void acceptOrder(OrderDto orderDto, Integer userId) {
        orderValidator.acceptOrderValidator(orderDto, userId);
        var order = orderMapper.dtoToOrder(orderDto);
        order.setOrderState(OrderState.APPROVED);
        orderRepository.save(order);
    }
}
