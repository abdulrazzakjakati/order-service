package com.codedecode.order.dto;

public record UserDTO(
         Long userId,
         String userName,
         String userPassword,
         String address,
         String city) {
}
