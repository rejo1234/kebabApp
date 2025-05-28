package com.restaurantApp.test.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetOrderListRequest {
    private final List<Integer> repositoryIdList;
    private final List<Integer> restaurantIdList;
    private final OrderState orderState;
    private final LocalDate createDateFrom;
    private final LocalDate createDateTo;
    private final LocalDate pickUpDateFrom;
    private final LocalDate pickUpDateTo;
    private final String orderName;
    private final String comment;
    private final String productName;
    private final Double productAmount;
}
