package com.restaurantApp.test.Order;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderListRequest{
    List<Integer> repositoryIdList;
    List<Integer> restaurantIdList;
}
