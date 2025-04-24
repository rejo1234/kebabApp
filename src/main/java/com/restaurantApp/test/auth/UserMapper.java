package com.restaurantApp.test.auth;

import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {
    RepositoryRepository repositoryRepository;
    RestaurantRepository restaurantRepository;


    public User dtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }
}
