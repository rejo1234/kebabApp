package com.restaurantApp.test.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    @NotBlank(message = "Nazwa produktu nie może być pusta")
    private String name;
    @DecimalMin(value = "0.1", message = "Ilość musi być przynajmniej 1")
    private Double weight;
    private Integer repositoryId;
}
