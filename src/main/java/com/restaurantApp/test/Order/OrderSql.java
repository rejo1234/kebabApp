package com.restaurantApp.test.Order;

public class OrderSql {
    public static final String BASE_QUERY = "SELECT o FROM Order o WHERE 1=1";
    public static final String REPO_ID_LIST_PARAM = "repositoryIdList";
    public static final String REPO_ID_LIST_QUERY = " AND o.repository.id IN (:" + REPO_ID_LIST_PARAM + ")";
    public static final String RESTAURANT_ID_LIST_PARAM = "restaurantIdList";
    public static final String RESTAURANT_ID_LIST_QUERY = " AND o.restaurant.id IN (:" + RESTAURANT_ID_LIST_PARAM + ")";
    public static final String ORDER_STATE_PARAM = "orderState";
    public static final String ORDER_STATE_QUERY = " AND o.orderState = :" + ORDER_STATE_PARAM;
    public static final String GET_CREATE_TIME_START_PARAM = "createTimeStart";
    public static final String GET_CREATE_TIME_START_QUERY = " AND o.createTime >= :" + GET_CREATE_TIME_START_PARAM;
    public static final String GET_CREATE_TIME_END_PARAM = "createTimeEnd";
    public static final String GET_CREATE_TIME_END_QUERY = " AND o.createTime <= :" + GET_CREATE_TIME_END_PARAM;
    public static final String DATE_IS_BETWEEN_CREATE_TIME_QUERY =
            " AND o.createTime BETWEEN :" + GET_CREATE_TIME_START_PARAM + " AND :" + GET_CREATE_TIME_END_PARAM;
    public static final String GET_DATE_TO_PICK_UP_START_PARAM = "dateToPickUpStart";
    public static final String GET_DATE_TO_PICK_UP_START_QUERY = " AND o.dateToPickUpStart >= :" + GET_DATE_TO_PICK_UP_START_PARAM;
    public static final String GET_DATE_TO_PICK_UP_END_PARAM = "dateToPickUpEnd";
    public static final String GET_DATE_TO_PICK_UP_END_QUERY = " AND o.dateToPickUpEnd <= :" + GET_DATE_TO_PICK_UP_END_PARAM;
    public static final String DATE_IS_BETWEEN_PICK_UP_TIME_QUERY =
            " AND o.dateToPickUpStart BETWEEN :" + GET_DATE_TO_PICK_UP_START_PARAM + " AND :" + GET_DATE_TO_PICK_UP_END_PARAM;
    public static final String SEARCH_TEXT_PARAM = "searchText";
    public static final String ORDER_NAME_OR_COMMENT_FILTER_QUERY =
            " AND (LOWER(o.orderName) LIKE LOWER(CONCAT('%', :" + SEARCH_TEXT_PARAM + ", '%')) " +
                    "OR LOWER(o.spaceForComment) LIKE LOWER(CONCAT('%', :" + SEARCH_TEXT_PARAM + ", '%')))";
    public static final String PRODUCT_DTO_LIST_FILTER_JOIN =
            " JOIN o.orderProductDtoList p ";
    public static final String SEARCH_PRODUCT_NAME_PARAM = "searchProductName";

    public static final String PRODUCT_NAME_FILTER_QUERY =
            " AND LOWER(p.name) LIKE LOWER(CONCAT('%', :" + SEARCH_PRODUCT_NAME_PARAM + ", '%'))";
    public static final String PRODUCT_AMOUNT_PARAM = "productAmount";
    public static final String PRODUCT_AMOUNT_FILTER_QUERY =
            " AND p.amount = :" + PRODUCT_AMOUNT_PARAM;
    public static final String BASE_SORT_QUERY = " ORDER BY";
}
