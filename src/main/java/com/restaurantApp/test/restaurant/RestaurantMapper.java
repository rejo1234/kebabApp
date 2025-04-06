package com.restaurantApp.test.restaurant;

import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public Restaurant dtoToRestaurantWithOutAddress(CreateRestaurantRequest createRestaurantRequest){
      return  Restaurant.builder()
              .name(createRestaurantRequest.getRestaurantDto().getName())
              .city(createRestaurantRequest.getRestaurantDto().getCity())
              .build();
    }
    public Restaurant dtoToRestaurant(CreateRestaurantRequest createRestaurantRequest){
        return  Restaurant.builder()
                .name(createRestaurantRequest.getRestaurantDto().getName())
                .city(createRestaurantRequest.getRestaurantDto().getCity())
                .address(createRestaurantRequest.getRestaurantDto().getAddress())
                .build();
    }
}
