package com.restaurantApp.test.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderListRequest {
    List<Integer> repositoryIdList;
    List<Integer> restaurantIdList;
}
