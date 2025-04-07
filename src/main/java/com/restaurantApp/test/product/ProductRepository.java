package com.restaurantApp.test.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(String name);
    List<Product> findByWeightGreaterThan(int weight);

}
