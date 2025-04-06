package com.restaurantApp.test.restaurant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String city;
    private String address;
    @ManyToMany
    @JoinTable(
            name = "restaurant_product",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    //111
    @JsonBackReference
    private List<Product> productListRestaurant = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "restaurant_repository",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "repository_id")
    )
    @JsonBackReference
    private List<Repository> repositoryList = new ArrayList<>();

    @ManyToMany(mappedBy = "userListRestaurant")
    @JsonManagedReference
    private List<User> restaurantListUser = new ArrayList<>();
}
