package com.restaurantApp.test.repository;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor

public class RepositoryMapper {
    UserRepository userRepository;
    ProductRepository productRepository;
    RestaurantRepository restaurantRepository;

    public Repository dtoToRepository(RepositoryDto repositoryDto) {
        List<Restaurant> restaurantList = restaurantRepository.findAllById(repositoryDto.getRestaurantList());
        List<Product> productList = productRepository.findAllById(repositoryDto.getProductList());
        List<User> userList = userRepository.findAllById(repositoryDto.getUserList());
        return Repository.builder()
                .id(repositoryDto.getId())
                .name(repositoryDto.getName())
                .address(repositoryDto.getAddress())
                .restaurantList(restaurantList)
                .productList(productList)
                .userList(userList)
                .build();
    }
}
