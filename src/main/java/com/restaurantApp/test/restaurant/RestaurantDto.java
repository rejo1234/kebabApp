package com.restaurantApp.test.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private Integer id;
    private String name;
    private String city;
    private String address;
}
