package com.restaurantApp.test.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class SearchParam {
    List<Integer> restaurantIds;
    List<Integer> repositoryIds;
    OrderState orderState;
    LocalDate createTimeStart;
    LocalDate createTimeEnd;
    LocalDate dateToPickUpStart;
    LocalDate dateToPickUpEnd;
    String orderName;
    String comment;
    String productName;
    Double productAmount;
    Integer userId;
}
