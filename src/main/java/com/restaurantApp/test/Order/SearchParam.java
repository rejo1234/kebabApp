package com.restaurantApp.test.Order;

import com.restaurantApp.test.common.Sort;
import com.restaurantApp.test.common.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SearchParam {
    private final GetOrderListRequest getOrderListRequest;
    private final String searchText;
    private final List<Sort> sorts;
}
