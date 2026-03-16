package com.codedecode.order.service;

import com.codedecode.order.dto.*;
import com.codedecode.order.mapper.OrderMapper;
import com.codedecode.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SequenceGenerator sequenceGenerator;

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @BeforeEach
    void setUp() {
        orderService =
                new OrderService(orderRepository, sequenceGenerator, restTemplate, orderMapper);
    }

    @Test
    void testSaveOrder() {
        // Implement test logic for saveOrder method
        // Arrange
        long restaurantId = 1L;
        RestaurantDTO restaurant = new RestaurantDTO(restaurantId, "Test Restaurant",
                "Test Address", "Test Cuisine", "Test Description");
        List<FoodItemsDTO> foodItemList = List.of(
                new FoodItemsDTO(1L, "Pizza", "Delicious cheese pizza",
                        true, 500L, restaurantId, 10)
        );
        OrderDTOFromFE orderDTOFromFE = new OrderDTOFromFE(foodItemList, 1L, restaurant);
        UserDTO userDTO = new UserDTO(1L, "John Doe", "1234567890", "address", "dummy city");

        //Act
        when(sequenceGenerator.getSequenceNumber()).thenReturn(1L);
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.eq(UserDTO.class))).thenReturn(userDTO);
        OrderDTO orderDTO = orderService.saveOrder(orderDTOFromFE);

        //Assert
        verify(restTemplate, times(1)).getForObject(anyString(), ArgumentMatchers.eq(UserDTO.class));
        assertEquals(1L, orderDTO.orderId());
        assertEquals(orderDTOFromFE.foodItemsList(), orderDTO.foodItemsList());
        assertEquals(orderDTOFromFE.restaurant(), orderDTO.restaurant());
        assertEquals(userDTO.userName(), orderDTO.userDTO().userName());

    }
}
