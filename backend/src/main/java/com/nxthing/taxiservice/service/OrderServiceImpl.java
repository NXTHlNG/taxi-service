package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.*;
import com.nxthing.taxiservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getActive() {
        return orderRepository.findAllByStatus(OrderStatus.LOOKING_FOR_DRIVER);
    }

    @Override
    public Order save(DraftOrder draftOrder, Tariff tariff) {
        return orderRepository.save(Order.builder()
                .origin(draftOrder.getOrigin())
                .destination(draftOrder.getDestination())
                .distance(draftOrder.getDistance())
                .duration(draftOrder.getDuration())
                .price(draftOrder.getPrices().get(tariff.getName()))
                .tariff(tariff)
                .status(OrderStatus.LOOKING_FOR_DRIVER)
                .customer(draftOrder.getCustomer())
                .date(LocalDateTime.now())
                .paymentMethod(draftOrder.getPaymentMethod())
                .build());
    }

    @Override
    @Transactional
    public Order accept(Long id, Driver driver) {
        Order order = orderRepository.findById(id).orElseThrow();

        if (order.getStatus() != OrderStatus.LOOKING_FOR_DRIVER ||
                driver.getStatus() != DriverStatus.LOOKING_FOR_ORDER ||
                order.getDriver() != null) {
            throw new RuntimeException();
        }

        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setDriver(driver);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order startWait(Long id, Driver driver) {
        Order order = orderRepository.findById(id).orElseThrow();

        if (order.getStatus() != OrderStatus.IN_PROGRESS ||
                driver.getStatus() != DriverStatus.ON_THE_WAY ||
                !Objects.equals(order.getDriver().getId(), driver.getId())) {
            throw new RuntimeException();
        }

        order.setStartWaitTime(LocalDateTime.now());

        order.setStatus(OrderStatus.WAITING);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order stopWait(Long id, Driver driver) {
        Order order = orderRepository.findById(id).orElseThrow();

        if (order.getStatus() != OrderStatus.WAITING ||
                driver.getStatus() != DriverStatus.WAITING ||
                !Objects.equals(order.getDriver().getId(), driver.getId())) {
            throw new RuntimeException();
        }

        order.setStopWaitTime(LocalDateTime.now());

        order.setStatus(OrderStatus.IN_PROGRESS);

        return orderRepository.save(order);
    }

    @Override
    public Order doneOrder(Long id, Driver driver) {
        Order order = orderRepository.findById(id).orElseThrow();

        if (order.getStatus() != OrderStatus.IN_PROGRESS ||
                driver.getStatus() != DriverStatus.ON_THE_WAY ||
                !Objects.equals(order.getDriver().getId(), driver.getId())) {
            throw new RuntimeException();
        }

        order.setStatus(OrderStatus.DONE);

        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllByCustomerUsername(String username) {
        return orderRepository.findAllByCustomerUsername(username);
    }

    @Override
    public List<Order> getAllByDriverUsername(String username) {
        return orderRepository.findAllByDriverUsername(username);
    }
}
