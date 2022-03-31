package com.nxthing.taxiservice.controller;

import com.nxthing.taxiservice.entity.District;
import com.nxthing.taxiservice.entity.car.CarType;
import com.nxthing.taxiservice.entity.order.Order;
import com.nxthing.taxiservice.entity.order.OrderStatus;
import com.nxthing.taxiservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Order createOrder(@RequestParam String customer,
                             @RequestParam CarType type,
                             @RequestParam int capacity,
                             @RequestParam District fromAddress,
                             @RequestParam District toAddress) {
        return orderService.createOrder(customer, type, capacity, fromAddress, toAddress);
    }
}
