package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.Driver;
import com.nxthing.taxiservice.entity.Order;

import java.util.List;

public interface OrderPublishService {
    void publish(Order order, List<Driver> drivers);

    void delete(Order order, List<Driver> drivers);

    void publishStartWaitingEvent(String username);

    void publishStopWaitingEvent(String username);

    void publishDoneOrderEvent(Order order);

    void publishCancelOrderEvent(Order order);

}
