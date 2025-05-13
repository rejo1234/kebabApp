package com.restaurantApp.test.auth;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.user.Role;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@AllArgsConstructor
public class ContextService {
    UserRepository userRepository;
    RepositoryRepository repositoryRepository;

    public void validateUserId(Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (!userId.equals(((User) principal).getId())) {
            System.out.println("Brak uprawnień do wykonania tej operacji");
            throw new AccessDeniedException("tutaj chcialem print ale nie leci w logach idk czemu");
        }
    }

    public void validateUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        User currentUser = (User) principal;
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            System.out.println("Brak uprawnień do wykonania tej operacji");
            throw new AccessDeniedException("tutaj chcialem print ale nie leci w logach idk czemu");
        }
    }

    public void validateRepositoryList(Integer repositoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        User currentUser = (User) principal;

        List<Integer> repositoryIdList = currentUser.getRepositoryList().stream()
                .map(Repository::getId)
                .toList();
        if (!repositoryIdList.contains(repositoryId)) {
            throw new AccessDeniedException("Brak uprawnień do aktualizacji tego produktu");
        }
    }

    public void validateRepositoryIdBelongsToRestaurant(Integer repositoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        User currentUser = (User) principal;

        List<Integer> repositoryIdList = currentUser.getRestaurantList().stream()
                .flatMap(restaurant -> restaurant.getRepositoryList().stream())
                .map(Repository::getId)
                .toList();

        if (!repositoryIdList.contains(repositoryId)) {
            throw new AccessDeniedException("Brak uprawnień do aktualizacji tego produktu");
        }
    }

    public void validateProductIdBelongsToRepository(Integer productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        User currentUser = (User) principal;

        List<Integer> productIdList = currentUser.getRepositoryList().stream()
                .flatMap(repository -> repository.getProductList().stream())
                .map(Product::getId)
                .toList();

        if (!productIdList.contains(productId)) {
            throw new AccessDeniedException("Brak uprawnień do aktualizacji tego produktu");
        }
    }

    public void validateRestaurantList(Integer restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        User currentUser = (User) principal;

        List<Integer> restaurantIdList = currentUser.getRestaurantList().stream()
                .map(Restaurant::getId)
                .toList();

        if (!restaurantIdList.contains(restaurantId)) {
            throw new AccessDeniedException("Brak uprawnień do aktualizacji tego produktu");
        }
    }
}
