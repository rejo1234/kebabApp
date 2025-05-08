package com.restaurantApp.test.Order;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date dateOfCreate;
    private Date dateToPickUp;
    private Integer repositoryId;
    private Integer restaurantId;
    private Integer userId;
//    @ElementCollection
//    @CollectionTable(
//            name = "order_products",            // nazwa tabeli kolekcji
//            joinColumns = @JoinColumn(name = "order_id")  // FK do tabeli orders
//    )
    private List<OrderProductDto> orderProductDtoList;
}
