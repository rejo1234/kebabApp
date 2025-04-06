package com.restaurantApp.test.repository;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repository")
public class Repository {
    @Id
    @GeneratedValue
    private Integer id;
    private String address;
    private String name;
    @ManyToMany(mappedBy = "userListRepository")
    @JsonManagedReference
    private List<User> restaurantListUser = new ArrayList<>();

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "repository_product",
            joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productListRepository = new ArrayList<>();

    @ManyToMany(mappedBy = "repositoryList")
    @JsonManagedReference
    private List<Restaurant> restaurantsList = new ArrayList<>();
}
