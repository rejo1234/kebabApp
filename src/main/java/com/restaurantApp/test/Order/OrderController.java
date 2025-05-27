package com.restaurantApp.test.Order;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PatchMapping("/modify")
    public ResponseEntity<Void> modifyOrder(
            @RequestBody ModifyOrderRequest modifyOrderRequest, @RequestParam Integer userId) {
        orderService.modifyOrder(modifyOrderRequest.getOrderDto(), userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(
            @RequestParam Integer orderId, @RequestParam Integer userId) {
        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteOrder(
            @RequestParam Integer orderId, @RequestParam Integer userId) {
        orderService.deleteOrder(orderId, userId);
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
                getOrderListRequest.getOrderState(),
                getOrderListRequest.getCreateTimeStart(),
                getOrderListRequest.getCreateTimeEnd(),
                getOrderListRequest.getDateToPickUpStart(),
                getOrderListRequest.getDateToPickUpEnd(),
                getOrderListRequest.getOrderName(),
                getOrderListRequest.getSpaceForComment(),
                getOrderListRequest.getProductName(),
                getOrderListRequest.getProductAmount(),
                userId));
    }    @PostMapping("/get-list2")
    public ResponseEntity<List<OrderDto>> getOrderList2(
            @RequestBody SearchParam searchParam, @RequestParam Integer userId
    ) {
        return ResponseEntity.ok(orderService.getOrdersList2(
                searchParam,
                userId));
    }
}
