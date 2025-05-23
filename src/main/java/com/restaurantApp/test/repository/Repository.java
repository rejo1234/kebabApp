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
    @ManyToMany(mappedBy = "repositoryList")
    @JsonManagedReference(value = "user-repository")
    private List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "repository", fetch = FetchType.EAGER)
    @JsonBackReference(value = "repository-product")
    private List<Product> productList = new ArrayList<>();

    @ManyToMany(mappedBy = "repositoryList")
    @JsonManagedReference(value = "restaurant-repository")
    private List<Restaurant> restaurantList = new ArrayList<>();
}
