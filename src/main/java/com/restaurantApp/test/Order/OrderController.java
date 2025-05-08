package com.restaurantApp.test.Order;


import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryDto;
import com.restaurantApp.test.repository.RepositoryService;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantService;
import com.restaurantApp.test.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    OrderService orderService;
    @PostMapping("/make")
    public ResponseEntity<Void> makeOrder(
            @RequestBody CreateOrderRequest createOrderRequest, @RequestParam Integer userId
    ) {
        orderService.makeOrder(createOrderRequest, userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/get-all-connected-to-restaurant")
    public ResponseEntity<List<OrderDto>> showOrdersConnectedToOneRestaurant(@RequestParam Integer restaurantId
            , @RequestParam Integer userId) {
        return ResponseEntity.ok(orderService.getOrdersForRestaurant(restaurantId, userId));
    }
}
