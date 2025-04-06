package com.restaurantApp.test.repository;

import com.restaurantApp.test.restaurant.CreateRestaurantRequest;
import com.restaurantApp.test.restaurant.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMapper {
    public Repository dtoToRepository(CreateRepositoryRequest createRepositoryRequest){
        return  Repository.builder()
                .name(createRepositoryRequest.getRepositoryDto().getName())
                .address(createRepositoryRequest.getRepositoryDto().getAddress())
                .build();
    }
}
