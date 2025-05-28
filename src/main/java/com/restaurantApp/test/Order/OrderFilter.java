package com.restaurantApp.test.Order;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderFilter {

    public static Specification<Order> buildFilter(SearchParam searchParam,List<Integer> restaurantIds, List<Integer> repositoryIds) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (restaurantIds != null && !restaurantIds.isEmpty()) {
                predicates.add(root.get("restaurant").get("id").in(restaurantIds));
            }
            if (repositoryIds != null && !repositoryIds.isEmpty()) {
                predicates.add(root.get("repository").get("id").in(repositoryIds));
            }
            if (searchParam.userId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), searchParam.userId));
            }
            if (searchParam.orderState != null) {
                predicates.add(cb.equal(root.get("orderState"), searchParam.orderState));
            }
            if (searchParam.createTimeStart != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateOfCreate"), searchParam.createTimeStart));
            }
            if (searchParam.createTimeEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateOfCreate"), searchParam.createTimeEnd));
            }
            if (searchParam.dateToPickUpStart != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateToPickUp"), searchParam.dateToPickUpStart));
            }
            if (searchParam.dateToPickUpEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateToPickUp"), searchParam.dateToPickUpEnd));
            }
            if (searchParam.orderName != null && !searchParam.orderName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("orderName")), "%" + searchParam.orderName.toLowerCase() + "%"));
            }
            if (searchParam.comment != null && !searchParam.comment.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("comment")), "%" + searchParam.comment.toLowerCase() + "%"));
            }
            if (searchParam.productName != null && !searchParam.productName.isBlank()) {
                Join<Object, Object> products = root.join("orderProductDtoList", JoinType.LEFT);
                predicates.add(cb.like(cb.lower(products.get("name")), "%" + searchParam.productName.toLowerCase() + "%"));
            }
            if (searchParam.productAmount != null) {
                Join<Object, Object> products = root.join("orderProductDtoList", JoinType.LEFT);
                predicates.add(cb.equal(products.get("amount"), searchParam.productAmount));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

