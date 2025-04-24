package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RestaurantMapper {

    UserRepository userRepository;
    RepositoryRepository repositoryRepository;

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
}
