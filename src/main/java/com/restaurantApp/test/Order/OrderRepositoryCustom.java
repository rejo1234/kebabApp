package com.restaurantApp.test.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> getOrderListRequest2(SearchParam2 searchParam2, List<Integer> repoIdList, List<Integer> restaurantIdList, Integer userId);

}
