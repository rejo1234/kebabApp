package com.restaurantApp.test.Order;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    private String orderName;
    private String spaceForComment;
    private LocalDateTime dateOfCreate;
    private LocalDateTime dateToPickUp;
    private Integer repositoryId;
    private Integer restaurantId;
    private Integer userId;
    private List<OrderProductDto> orderProductDtoList;
}
