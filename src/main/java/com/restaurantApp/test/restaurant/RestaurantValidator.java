package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@AllArgsConstructor
public class RestaurantValidator {
    private final ContextService contextService;

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

    public void validateRestaurantIdList(List<Integer> restaurantIds) {
        List<Integer> restaurantsIdList = contextService.getCurrentUser().getRestaurantList().stream()
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
}
