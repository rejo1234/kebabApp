package com.restaurantApp.test.auth;

import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@AllArgsConstructor
public class ContextService {
    private final UserRepository userRepository;
    private final RepositoryRepository repositoryRepository;
    private final ProductRepository productRepository;

    public List<Integer> getUserRepositoryIds() {
        return getUserRepositoryList().stream()
                .map(Repository::getId)
                .toList();
    }

    public List<Integer> getUserRestaurantIds() {
        return getUserRestaurantList().stream()
                .map(Restaurant::getId)
                .toList();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        return (User) principal;
    }

    public List<Repository> getUserRepositoryList() {
        return getCurrentUser().getRepositoryList();
    }

    public List<Restaurant> getUserRestaurantList() {
        return getCurrentUser().getRestaurantList();
    }

}
