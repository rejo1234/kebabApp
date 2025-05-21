package com.restaurantApp.test.user;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor
public class UserValidator {
    public void validateUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        User currentUser = (User) principal;
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            System.out.println("Brak uprawnień do wykonania tej operacji");
            throw new AccessDeniedException("tutaj chcialem print ale nie leci w logach idk czemu");
        }
    }

    public void validateUserId(Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (!userId.equals(((User) principal).getId())) {
            System.out.println("Brak uprawnień do wykonania tej operacji");
            throw new AccessDeniedException("tutaj chcialem print ale nie leci w logach idk czemu");
        }
    }
}
