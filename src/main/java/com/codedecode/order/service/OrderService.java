package com.codedecode.order.service;

import com.codedecode.order.dto.OrderDTO;
import com.codedecode.order.dto.OrderDTOFromFE;
import com.codedecode.order.dto.UserDTO;
import com.codedecode.order.entity.Order;
import com.codedecode.order.mapper.OrderMapper;
import com.codedecode.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SequenceGenerator sequenceGenerator;
    private final RestTemplate restTemplate;
    private final OrderMapper orderMapper;

    public OrderDTO saveOrder(OrderDTOFromFE orderDTOFromFE) {
        long newOrderId = sequenceGenerator.getSequenceNumber();
        UserDTO userDTO = fetchUserDetailsMS(orderDTOFromFE.userId());
        Order order = new Order(newOrderId, orderDTOFromFE.foodItemsList(), orderDTOFromFE.restaurant(), userDTO);
        orderRepository.save(order);
        return orderMapper.orderToOrderDTO(order);
    }

    private UserDTO fetchUserDetailsMS(Long userId) {
        return restTemplate.getForObject("http://USER-SERVICE/users/" + userId, UserDTO.class);
    }
}
