package com.codedecode.order.controller;

import com.codedecode.order.dto.*;
import com.codedecode.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    void testPlaceOrder() {
        // Implement test logic for placeOrder method
        // Arrange
        var restaurantId = 1L;
        List<FoodItemsDTO> foodItemList = List.of(
                new FoodItemsDTO(1L, "Pizza", "Delicious cheese pizza",
                        true, 500L, restaurantId, 10)
        );
        RestaurantDTO restaurant = new RestaurantDTO(restaurantId, "Test Restaurant",
                "Test Address", "Test Cuisine", "Test Description");
        OrderDTOFromFE orderDTOFromFE = new OrderDTOFromFE(foodItemList, 1L, restaurant);
        UserDTO userDTO = new UserDTO(1L, "John Doe", "1234567890", "address", "dummy city");
        OrderDTO orderDTO = new OrderDTO(1L, foodItemList, restaurant, userDTO);

        // Act
        when(orderService.saveOrder(orderDTOFromFE)).thenReturn(orderDTO); // Mock the service method if needed
        ResponseEntity<OrderDTO> orderDTOResponseEntity = orderController.placeOrder(orderDTOFromFE);

        //Assert
        verify(orderService, times(1)).saveOrder(orderDTOFromFE);
        assertEquals(orderDTO, orderDTOResponseEntity.getBody());
        assert orderDTOResponseEntity.getStatusCode() == HttpStatus.CREATED;
        assertEquals(Objects.requireNonNull(orderDTOResponseEntity.getBody()).userDTO(), userDTO);
        assertEquals(orderDTOResponseEntity.getBody().restaurant(), restaurant);
        assertEquals(orderDTOResponseEntity.getBody().foodItemsList(), foodItemList);
    }
}
