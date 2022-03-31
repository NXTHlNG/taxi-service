package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.District;
import com.nxthing.taxiservice.entity.car.CarType;
import com.nxthing.taxiservice.entity.order.Order;
import com.nxthing.taxiservice.entity.order.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final CarService carService;
    private final OrderPriceCalculationService orderPriceCalculationService;

    public OrderServiceImpl(CarService carService, OrderPriceCalculationService orderPriceCalculationService) {
        this.carService = carService;
        this.orderPriceCalculationService = orderPriceCalculationService;
    }

    @Override
    public Order createOrder(String customer, CarType carType, int capacity, District fromAddress, District toAddress) {
        return Order.builder()
                .customer(customer)
                .fromAddress(fromAddress)
                .toAddress(toAddress)
                .cars(carService.getCars(carType, capacity))
                .price(orderPriceCalculationService.calculatePrice(fromAddress, toAddress))
                .status(OrderStatus.IN_PROGRESS)
                .build();
    }
}
