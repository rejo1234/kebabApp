package com.restaurantApp.test.repository;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor

public class RepositoryMapper {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    public Repository dtoToRepository(RepositoryDto repositoryDto) {
        List<Restaurant> restaurantList = restaurantRepository.findAllById(repositoryDto.getRestaurantListId());
        List<Product> productList = productRepository.findAllById(repositoryDto.getProductListId());
        List<User> userList = userRepository.findAllById(repositoryDto.getUserListId());
        return Repository.builder()
                .id(repositoryDto.getId())
                .name(repositoryDto.getName())
                .address(repositoryDto.getAddress())
                .restaurantList(restaurantList)
                .productList(productList)
                .userList(userList)
                .build();
    }

    public RepositoryDto repositoryToDto(Repository repository) {
        List<Integer> listRestaurant = repository.getRestaurantList().stream()
                .map(Restaurant::getId)
                .toList();

        List<Integer> listUserId = repository.getUserList().stream()
                .map(User::getId)
                .toList();

        List<Integer> listProductId = repository.getProductList().stream()
                .map(Product::getId)
                .toList();

        return RepositoryDto.builder()
                .id(repository.getId())
                .name(repository.getName())
                .address(repository.getAddress())
                .restaurantListId(listRestaurant)
                .userListId(listUserId)
                .productListId(listProductId)
                .build();
    }
}
