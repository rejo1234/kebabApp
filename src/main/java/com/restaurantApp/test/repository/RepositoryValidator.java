package com.restaurantApp.test.repository;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@AllArgsConstructor

public class RepositoryValidator {
    private final ContextService contextService;
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
    public void validateRepositoryIdList(List<Integer> repositoryIdList) {
        List<Integer> repositoryIdsList = contextService.getCurrentUser().getRepositoryList().stream()
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
}
