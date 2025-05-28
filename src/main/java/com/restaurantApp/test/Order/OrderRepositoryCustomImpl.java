package com.restaurantApp.test.Order;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.restaurantApp.test.Order.OrderSql.*;


public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> getOrderListRequest2(SearchParam2 searchParam2, List<Integer> repoIdList, List<Integer> restaurantIdList, Integer userId) {
        StringBuilder queryBuilder = new StringBuilder(BASE_QUERY);
        Map<String, Object> valueParamsMap = new HashMap<>();
        if (!searchParam2.getGetOrderListRequest().getRepositoryIdList().isEmpty()) {
            queryBuilder.append(REPO_ID_LIST_QUERY);
            valueParamsMap.put(REPO_ID_LIST_PARAM, searchParam2.getGetOrderListRequest().getRepositoryIdList());
        }
        if (!searchParam2.getGetOrderListRequest().getRestaurantIdList().isEmpty()) {
            queryBuilder.append(RESTAURANT_ID_LIST_QUERY);
            valueParamsMap.put(RESTAURANT_ID_LIST_PARAM, searchParam2.getGetOrderListRequest().getRestaurantIdList());

        }
        if (searchParam2.getGetOrderListRequest().getOrderState() != null) {
            queryBuilder.append(ORDER_STATE_QUERY);
            valueParamsMap.put(ORDER_STATE_PARAM, searchParam2.getGetOrderListRequest().getOrderState());

        }
        if (searchParam2.getGetOrderListRequest().getCreateDateFrom() != null) {
            queryBuilder.append(GET_CREATE_TIME_START_QUERY);
            valueParamsMap.put(GET_CREATE_TIME_START_PARAM, searchParam2.getGetOrderListRequest().getCreateDateFrom());
        }
        if (searchParam2.getGetOrderListRequest().getCreateDateTo() != null) {
            queryBuilder.append(GET_CREATE_TIME_END_QUERY);
            valueParamsMap.put(GET_CREATE_TIME_END_PARAM, searchParam2.getGetOrderListRequest().getCreateDateTo());

        }
        if (searchParam2.getGetOrderListRequest().getCreateDateFrom() != null &&
                searchParam2.getGetOrderListRequest().getCreateDateTo() != null) {
            queryBuilder.append(DATE_IS_BETWEEN_CREATE_TIME_QUERY);
            valueParamsMap.put(GET_CREATE_TIME_START_PARAM, searchParam2.getGetOrderListRequest().getCreateDateFrom().atStartOfDay());
            valueParamsMap.put(GET_CREATE_TIME_END_PARAM, searchParam2.getGetOrderListRequest().getCreateDateTo().atTime(23, 59, 59));
        }
        if (searchParam2.getGetOrderListRequest().getPickUpDateFrom() != null) {
            queryBuilder.append(GET_DATE_TO_PICK_UP_START_QUERY);
            valueParamsMap.put(GET_DATE_TO_PICK_UP_START_PARAM, searchParam2.getGetOrderListRequest().getPickUpDateFrom());

        }
        if (searchParam2.getGetOrderListRequest().getPickUpDateTo() != null) {
            queryBuilder.append(GET_DATE_TO_PICK_UP_END_QUERY);
            valueParamsMap.put(GET_DATE_TO_PICK_UP_END_PARAM, searchParam2.getGetOrderListRequest().getPickUpDateTo());

        }
        if (searchParam2.getGetOrderListRequest().getPickUpDateFrom() != null &&
                searchParam2.getGetOrderListRequest().getPickUpDateTo() != null) {
            queryBuilder.append(DATE_IS_BETWEEN_PICK_UP_TIME_QUERY);
            valueParamsMap.put(GET_DATE_TO_PICK_UP_START_PARAM, searchParam2.getGetOrderListRequest().getPickUpDateFrom().atStartOfDay());
            valueParamsMap.put(GET_DATE_TO_PICK_UP_END_PARAM, searchParam2.getGetOrderListRequest().getPickUpDateTo().atTime(23, 59, 59));
        }
        if (searchParam2.getSearchText() != null) {
            queryBuilder.append(ORDER_NAME_OR_COMMENT_FILTER_QUERY);
            valueParamsMap.put(SEARCH_TEXT_PARAM, searchParam2.getSearchText().toLowerCase());
        }
        if (searchParam2.getGetOrderListRequest().getProductName() != null
                || searchParam2.getGetOrderListRequest().getProductAmount() != null) {
            queryBuilder.append(PRODUCT_DTO_LIST_FILTER_JOIN);
        }
        if (searchParam2.getGetOrderListRequest().getProductName() != null) {
            queryBuilder.append(PRODUCT_NAME_FILTER_QUERY);
            valueParamsMap.put(SEARCH_PRODUCT_NAME_PARAM, searchParam2.getGetOrderListRequest().getProductName().toLowerCase());
        }
        if (searchParam2.getGetOrderListRequest().getProductAmount() != null) {
            queryBuilder.append(PRODUCT_AMOUNT_FILTER_QUERY);
            valueParamsMap.put(PRODUCT_AMOUNT_PARAM, searchParam2.getGetOrderListRequest().getProductAmount());
        }
        if (!Collections.isEmpty(searchParam2.getSorts())) {
            StringBuilder sortQueryBuilder = new StringBuilder();
            sortQueryBuilder.append(BASE_SORT_QUERY);
            for (var sort : searchParam2.getSorts()) {
                sortQueryBuilder.append(" o.").append(sort.getField()).append(" ").append(sort.getDirection().name());
            }
            queryBuilder.append(sortQueryBuilder);
        }
        TypedQuery<Order> query = entityManager.createQuery(queryBuilder.toString(), Order.class);
        valueParamsMap.forEach(query::setParameter);
        return query.getResultList();
    }
}

