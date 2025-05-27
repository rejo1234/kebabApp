package com.restaurantApp.test.Order;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderFilter {

    public Specification<Order> buildFilter(
            List<Integer> restaurantIds,
            List<Integer> repositoryIds,
            OrderState orderState,
            LocalDate createTimeStart,
            LocalDate createTimeEnd,
            LocalDate dateToPickUpStart,
            LocalDate dateToPickUpEnd,
            String orderName,
            String spaceForComment,
            String productName,
            Integer productAmount,
            Integer userId) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (restaurantIds != null && !restaurantIds.isEmpty()) {
                predicates.add(root.get("restaurant").get("id").in(restaurantIds));
            }
            if (repositoryIds != null && !repositoryIds.isEmpty()) {
                predicates.add(root.get("repository").get("id").in(repositoryIds));
            }
            if (userId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), userId));
            }
            if (orderState != null) {
                predicates.add(cb.equal(root.get("orderState"), orderState));
            }
            if (createTimeStart != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateOfCreate"), createTimeStart));
            }
            if (createTimeEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateOfCreate"), createTimeEnd));
            }
            if (dateToPickUpStart != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateToPickUp"), dateToPickUpStart));
            }
            if (dateToPickUpEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateToPickUp"), dateToPickUpEnd));
            }
            if (orderName != null && !orderName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("orderName")), "%" + orderName.toLowerCase() + "%"));
            }
            if (spaceForComment != null && !spaceForComment.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("spaceForComment")), "%" + spaceForComment.toLowerCase() + "%"));
            }
            if (productName != null && !productName.isBlank()) {
                Join<Object, Object> products = root.join("orderProductDtoList", JoinType.LEFT);
                predicates.add(cb.like(cb.lower(products.get("name")), "%" + productName.toLowerCase() + "%"));
            }
            if (productAmount != null) {
                Join<Object, Object> products = root.join("orderProductDtoList", JoinType.LEFT);
                predicates.add(cb.equal(products.get("amount"), productAmount));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

