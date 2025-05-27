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
    public List<Order> getOrderListRequest2(SearchParam searchParam, List<Integer> repoIdList, List<Integer> restaurantIdList, Integer userId) {
        StringBuilder queryBuilder = new StringBuilder(BASE_QUERY);
        Map<String, Object> valueParamsMap = new HashMap<>();
        if (!searchParam.getGetOrderListRequest().getRepositoryIdList().isEmpty()) {
            queryBuilder.append(REPO_ID_LIST_QUERY);
            valueParamsMap.put(REPO_ID_LIST_PARAM, searchParam.getGetOrderListRequest().getRepositoryIdList());
        }
        if (!searchParam.getGetOrderListRequest().getRestaurantIdList().isEmpty()) {
            queryBuilder.append(RESTAURANT_ID_LIST_QUERY);
            valueParamsMap.put(RESTAURANT_ID_LIST_PARAM, searchParam.getGetOrderListRequest().getRestaurantIdList());

        }
        if (searchParam.getGetOrderListRequest().getOrderState() != null) {
            queryBuilder.append(ORDER_STATE_QUERY);
            valueParamsMap.put(ORDER_STATE_PARAM, searchParam.getGetOrderListRequest().getOrderState());

        }
        if (searchParam.getGetOrderListRequest().getCreateTimeStart() != null) {
            queryBuilder.append(GET_CREATE_TIME_START_QUERY);
            valueParamsMap.put(GET_CREATE_TIME_START_PARAM, searchParam.getGetOrderListRequest().getCreateTimeStart());
        }
        if (searchParam.getGetOrderListRequest().getCreateTimeEnd() != null) {
            queryBuilder.append(GET_CREATE_TIME_END_QUERY);
            valueParamsMap.put(GET_CREATE_TIME_END_PARAM, searchParam.getGetOrderListRequest().getCreateTimeEnd());

        }
        if (searchParam.getGetOrderListRequest().getCreateTimeStart() != null &&
                searchParam.getGetOrderListRequest().getCreateTimeEnd() != null) {
            queryBuilder.append(DATE_IS_BETWEEN_CREATE_TIME_QUERY);
            valueParamsMap.put(GET_CREATE_TIME_START_PARAM, searchParam.getGetOrderListRequest().getCreateTimeStart().atStartOfDay());
            valueParamsMap.put(GET_CREATE_TIME_END_PARAM, searchParam.getGetOrderListRequest().getCreateTimeEnd().atTime(23, 59, 59));
        }
        if (searchParam.getGetOrderListRequest().getDateToPickUpStart() != null) {
            queryBuilder.append(GET_DATE_TO_PICK_UP_START_QUERY);
            valueParamsMap.put(GET_DATE_TO_PICK_UP_START_PARAM, searchParam.getGetOrderListRequest().getDateToPickUpStart());

        }
        if (searchParam.getGetOrderListRequest().getDateToPickUpEnd() != null) {
            queryBuilder.append(GET_DATE_TO_PICK_UP_END_QUERY);
            valueParamsMap.put(GET_DATE_TO_PICK_UP_END_PARAM, searchParam.getGetOrderListRequest().getDateToPickUpEnd());

        }
        if (searchParam.getGetOrderListRequest().getDateToPickUpStart() != null &&
                searchParam.getGetOrderListRequest().getDateToPickUpEnd() != null) {
            queryBuilder.append(DATE_IS_BETWEEN_PICK_UP_TIME_QUERY);
            valueParamsMap.put(GET_DATE_TO_PICK_UP_START_PARAM, searchParam.getGetOrderListRequest().getDateToPickUpStart().atStartOfDay());
            valueParamsMap.put(GET_DATE_TO_PICK_UP_END_PARAM, searchParam.getGetOrderListRequest().getDateToPickUpEnd().atTime(23, 59, 59));
        }
        if (searchParam.getSearchText() != null) {
            queryBuilder.append(ORDER_NAME_OR_COMMENT_FILTER_QUERY);
            valueParamsMap.put(SEARCH_TEXT_PARAM, searchParam.getSearchText().toLowerCase());
        }
        if (searchParam.getGetOrderListRequest().getProductName() != null
                || searchParam.getGetOrderListRequest().getProductAmount() != null) {
            queryBuilder.append(PRODUCT_DTO_LIST_FILTER_JOIN);
        }
        if (searchParam.getGetOrderListRequest().getProductName() != null) {
            queryBuilder.append(PRODUCT_NAME_FILTER_QUERY);
            valueParamsMap.put(SEARCH_PRODUCT_NAME_PARAM, searchParam.getGetOrderListRequest().getProductName().toLowerCase());
        }
        if (searchParam.getGetOrderListRequest().getProductAmount() != null) {
            queryBuilder.append(PRODUCT_AMOUNT_FILTER_QUERY);
            valueParamsMap.put(PRODUCT_AMOUNT_PARAM, searchParam.getGetOrderListRequest().getProductAmount());
        }
        if (!Collections.isEmpty(searchParam.getSorts())) {
            StringBuilder sortQueryBuilder = new StringBuilder();
            sortQueryBuilder.append(BASE_SORT_QUERY);
            for (var sort : searchParam.getSorts()) {
                sortQueryBuilder.append(" o.").append(sort.getField()).append(" ").append(sort.getDirection().name());
            }
            queryBuilder.append(sortQueryBuilder);
        }
        TypedQuery<Order> query = entityManager.createQuery(queryBuilder.toString(), Order.class);
        valueParamsMap.forEach(query::setParameter);
        return query.getResultList();
    }
}

