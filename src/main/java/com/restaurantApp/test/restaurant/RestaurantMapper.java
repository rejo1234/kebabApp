package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RestaurantMapper {
    private final UserRepository userRepository;
    private final RepositoryRepository repositoryRepository;


    public Restaurant dtoToRestaurant(RestaurantDto restaurantDto) {
        List<Repository> repositoryList = repositoryRepository.findAllById(restaurantDto.getUserIdList());
        List<User> userList = userRepository.findAllById(restaurantDto.getUserIdList());
        return Restaurant.builder()
                .id(restaurantDto.getId())
                .name(restaurantDto.getName())
                .city(restaurantDto.getCity())
                .address(restaurantDto.getAddress())
                .repositoryList(repositoryList)
                .userList(userList)
                .build();
    }

    public RestaurantDto restaurantToRestaurantDto(Restaurant restaurant) {
        List<Integer> listRepositoryId = restaurant.getRepositoryList().stream()
                .map(Repository::getId)
                .toList();

        List<Integer> listUserId = restaurant.getUserList().stream()
                .map(User::getId)
                .toList();

        return RestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .city(restaurant.getCity())
                .address(restaurant.getAddress())
                .repositoryList(listRepositoryId)
                .userIdList(listUserId)
                .build();
    }
}
