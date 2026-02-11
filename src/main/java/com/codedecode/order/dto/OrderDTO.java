package com.codedecode.order.dto;

import java.util.List;

public record OrderDTO(
        Long orderId,
        List<FoodItemsDTO> foodItemsList,
        RestaurantDTO restaurant,
        UserDTO userDTO
) {}
