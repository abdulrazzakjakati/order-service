package com.codedecode.order.dto;

import java.util.List;

public record OrderDTOFromFE(
        List<FoodItemsDTO> foodItemsList,
        Long userId,
        RestaurantDTO restaurant
) {
}
