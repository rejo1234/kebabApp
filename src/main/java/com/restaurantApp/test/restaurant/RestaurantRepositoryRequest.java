package com.restaurantApp.test.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRepositoryRequest {
    int idRestaurant;
    int idRepository;
}
