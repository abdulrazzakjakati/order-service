package com.codedecode.order.controller;

import com.codedecode.order.dto.OrderDTO;
import com.codedecode.order.dto.OrderDTOFromFE;
import com.codedecode.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTOFromFE orderDTOFromFE) {
        OrderDTO savedOrder = orderService.saveOrder(orderDTOFromFE);
        return ResponseEntity.status(201).body(savedOrder);
    }
}
