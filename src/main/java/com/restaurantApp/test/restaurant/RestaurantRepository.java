package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
