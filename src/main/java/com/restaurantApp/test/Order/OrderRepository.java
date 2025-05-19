package com.restaurantApp.test.Order;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByOrderNameContainingIgnoreCase(String orderName);
    List<Order> findAllBySpaceForCommentContainingIgnoreCase(String commentPart);
  //  List<Order> findAllByOrderByDateToPickUpAsc(LocalDateTime pickUpDate);
    List<Order> findAllByOrderState(OrderState orderState);
    List<Order> findAllByRestaurantIdInAndUserId(List<Integer> restaurantIds, Integer userId);
    List<Order> findAllByRepositoryIdInAndUserId(List<Integer> repositoryIds, Integer userId);
    List<Order> findAllByRestaurantIdInAndRepositoryIdInAndUserId(
            List<Integer>  repositoryIds,
            List<Integer> restaurantIds,
            Integer userId
    );
    List<Order> findAllByRestaurantIdInAndRepositoryIdInAndUserIdAndDateOfCreateBetween(
            List<Integer> repositoryIds,
            List<Integer> restaurantIds,
            Integer userId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
}
