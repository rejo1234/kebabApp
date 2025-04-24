package com.restaurantApp.test.auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryDto;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantDto;
import com.restaurantApp.test.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private List<Integer> restaurantList;
    private List<Integer> repositoryList;
}
