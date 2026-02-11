package com.codedecode.order.dto;

public record FoodItemsDTO(Long id,
                           String itemName,
                           String itemDescription,
                           Boolean isVeg,
                           Long price,
                           Integer restaurantId,
                           Integer quantity) {
}
