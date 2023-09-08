package com.example.orderservice.dto;

import lombok.Data;

@Data
public class OrderDto {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
