package com.example.orderservice.service;


import com.example.orderservice.domain.OrderEntity;
import com.example.orderservice.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderEntity> getOrdersByUserId(String userId);
    OrderDto getOrderByOrderId(String orderId);
    OrderDto createOrder(OrderDto orderDetails);
}
