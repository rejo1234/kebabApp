package com.restaurantApp.test.auth;

import com.restaurantApp.test.user.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }
}
