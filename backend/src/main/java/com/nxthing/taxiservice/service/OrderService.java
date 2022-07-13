package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.DraftOrder;
import com.nxthing.taxiservice.entity.Driver;
import com.nxthing.taxiservice.entity.Order;
import com.nxthing.taxiservice.entity.Tariff;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Order accept(Long id, Driver driver);

    List<Order> getActive();

    Order save(DraftOrder draftOrder, Tariff byName);

    Order startWait(Long id, Driver driver);

    Order stopWait(Long id, Driver driver);

    Order doneOrder(Long id, Driver driver);

    Order getById(Long id);

    Order save(Order order);

    List<Order> getAllByCustomerUsername(String username);

    List<Order> getAllByDriverUsername(String username);
}
