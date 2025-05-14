package com.restaurantApp.test.auth;

import com.restaurantApp.test.Order.OrderDto;
import com.restaurantApp.test.Order.OrderState;
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

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ContextService {
    UserRepository userRepository;
    RepositoryRepository repositoryRepository;
    public void validateRepositoryIdList(List<Integer> repositoryIdList) {
        List<Integer> repositoryIdsList = getCurrentUser().getRepositoryList().stream()
                .map(Repository::getId)
                .toList();

        List<Integer> unauthorized = repositoryIdList.stream()
                .filter(id -> !repositoryIdsList.contains(id))
                .toList();

        if (!unauthorized.isEmpty()) {
            throw new AccessDeniedException(
                    "Brak uprawnień do wykonania tej operacji"
            );
        }
    }
    public void validateRestaurantIdList(List<Integer> restaurantIds) {
        List<Integer> restaurantsIdList = getCurrentUser().getRestaurantList().stream()
                .map(Restaurant::getId)
                .toList();

        List<Integer> unauthorized = restaurantIds.stream()
                .filter(id -> !restaurantsIdList.contains(id))
                .toList();

        if (!unauthorized.isEmpty()) {
            throw new AccessDeniedException(
                    "Brak uprawnień do wykonania tej operacji"
            );
        }
    }
    public void validateTime(LocalDateTime timeToPickUp){
        if (LocalDateTime.now().isBefore(timeToPickUp)){
            throw new AccessDeniedException("Godzina jest bledna");
        }
    }
    public void validateOrderStatus(OrderDto orderDto) {
        if (orderDto.getOrderState() == OrderState.APPROVED || orderDto.getOrderState() == OrderState.REJECTED) {
            System.out.println("Nie mozesz zmodyfikować zamowienia");
            throw new AccessDeniedException("status jest bledny");
        }
    }

    public void validateDeleteOrder(OrderDto orderDto) {
        if (orderDto.getOrderState() == OrderState.APPROVED || orderDto.getOrderState() == OrderState.REJECTED) {
            System.out.println("Nie mozesz usunac zamowienia");
            throw new AccessDeniedException("status jest bledny");
        }
    }

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

    public void validateRepositoryId(Integer repositoryId) {
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

    public void validateRestaurantId(Integer restaurantId) {
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
