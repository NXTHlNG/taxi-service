package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.District;
import com.nxthing.taxiservice.entity.car.CarType;
import com.nxthing.taxiservice.entity.order.Order;

public interface OrderService {
    Order createOrder(String customer, CarType carType, int capacity, District fromAddress, District toAddress);
}
