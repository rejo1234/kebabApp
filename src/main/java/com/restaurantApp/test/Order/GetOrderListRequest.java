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
    private final LocalDate createTimeStart;
    private final LocalDate createTimeEnd;
    private final LocalDate dateToPickUpStart;
    private final LocalDate dateToPickUpEnd;
    private final String orderName;
    private final String spaceForComment;
    private final String productName;
    private final Integer productAmount;
}
