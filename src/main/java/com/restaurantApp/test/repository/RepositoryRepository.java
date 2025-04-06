package com.restaurantApp.test.repository;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryRepository extends JpaRepository<Repository, Integer> {

}
