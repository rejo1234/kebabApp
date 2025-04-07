package com.restaurantApp.test.product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double weight;
    @ManyToMany(mappedBy = "productListRestaurant")
    @JsonManagedReference
    private List<Restaurant> productListRestaurant = new ArrayList<>();

    @ManyToMany(mappedBy = "productListRepository")
    @JsonManagedReference
    private List<Repository> productListRepository = new ArrayList<>();
}
