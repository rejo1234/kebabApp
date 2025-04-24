package com.restaurantApp.test.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryDto {
    private Integer id;
    private String address;
    private String name;
    private List<Integer> productList;
    private List<Integer> restaurantList;
    private List<Integer> userList;
}
