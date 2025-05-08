package com.restaurantApp.test.Order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;
    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    private String orderName;
    private String spaceForComment;
    private Date dateOfCreate;
    private Date dateToPickUp;
    @ElementCollection
    @CollectionTable(
            name = "orders_products",            // nazwa tabeli kolekcji
            joinColumns = @JoinColumn(name = "orders_id")  // FK do tabeli orders
    )
    private List<OrderProductDto> orderProductDtoList;

    @ManyToOne
    @JoinColumn(name = "repository_id")
    @JsonManagedReference(value = "repository-order")
    private Repository repository;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonManagedReference(value = "restaurant-order")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference(value = "user-order")
    private User user;

    @PrePersist
    private void assignOrderName() {
        this.orderName = "zamówienie " + this.id;
    }
}
