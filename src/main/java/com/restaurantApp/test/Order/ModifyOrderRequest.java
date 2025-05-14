package com.restaurantApp.test.Order;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ModifyOrderRequest extends CreateOrderRequest{
}
