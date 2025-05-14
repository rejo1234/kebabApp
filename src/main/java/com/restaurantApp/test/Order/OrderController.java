package com.restaurantApp.test.Order;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    OrderService orderService;

    @PatchMapping("/modify-order")
    public ResponseEntity<Void> updateOrder(
            @RequestBody ModifyOrderRequest modifyOrderRequest, @RequestParam Integer userId) {
        orderService.modifyOrder(modifyOrderRequest.getOrderDto(), userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cancel-order")
    public ResponseEntity<Void> cancelOrder(
            @RequestBody CreateOrderRequest createOrderRequest, @RequestParam Integer userId) {
        orderService.cancelOrder(createOrderRequest.getOrderDto(), userId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(
            @RequestBody CreateOrderRequest createOrderRequest, @RequestParam Integer userId
    ) {
        orderService.createOrder(createOrderRequest.getOrderDto(), userId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/get-list")
    public ResponseEntity<List<OrderDto>> getOrderList(
            @RequestBody GetOrderListRequest getOrderListRequest, @RequestParam Integer userId
    ) {
        return ResponseEntity.ok(orderService.getOrdersList(
                getOrderListRequest.getRestaurantIdList(),
                getOrderListRequest.getRepositoryIdList(),
                userId));
    }
}
