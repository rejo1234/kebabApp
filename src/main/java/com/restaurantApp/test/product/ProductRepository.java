package com.restaurantApp.test.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByNameProduct(String name);
    List<Product> findByKgsGreaterThan(int kg);
    @Override
    @NonNull
    List<Product> findAll();
}
