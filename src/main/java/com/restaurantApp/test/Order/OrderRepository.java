package com.restaurantApp.test.Order;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByRestaurantIdInAndRepositoryIdInAndUserId(
            List<Integer> restaurantIds,
            List<Integer> repositoryIds,
            Integer userId
    );
}
