package com.restaurantApp.test.auth;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthenticateContextService {
    UserRepository userRepository;
    RepositoryRepository repositoryRepository;
    public void authenticateUserId(Integer userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (!userId.equals(((User) principal).getId())){
            System.out.println("Brak uprawnień do wykonania tej operacji");
            throw new AccessDeniedException("tutaj chcialem print ale nie leci w logach idk czemu");
        }
    }
    @Transactional
    public void authenticateRepositoryList(Integer repositoryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();

        User currentUser = (User) principal;
        User user = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User nie istnieje"));

        List<Integer> userListRepositoryId = user.getRepositoryList().stream()
                .map(Repository::getId)
                .toList();


        List<Integer> listRepositoryInRestaurantList = user.getRestaurantList().stream()
                .flatMap(restaurant -> restaurant.getRepositoryList().stream())
                .map(Repository::getId)
                .toList();

        if (userListRepositoryId.contains(repositoryId) && listRepositoryInRestaurantList.contains(repositoryId)){
            System.out.println("okej");
        }
        else throw new AccessDeniedException("Brak uprawnień do aktualizacji tego produktu");
    }
    public void authenticateRestaurantList(Integer restaurantId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        User currentUser = (User) principal;

        List<Integer> restaurantList = currentUser.getRestaurantList().stream()
                .map(Restaurant::getId)
                .toList();

        if (restaurantList.contains(restaurantId)){
            System.out.println("okej");
        }
        else throw new AccessDeniedException("Brak uprawnień do aktualizacji tego produktu");
    }
}
