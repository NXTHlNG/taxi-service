package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.OrderDto;
import com.nxthing.taxiservice.dto.StompEvent;
import com.nxthing.taxiservice.entity.Driver;
import com.nxthing.taxiservice.entity.Order;
import com.nxthing.taxiservice.entity.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPublishServiceImpl implements OrderPublishService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public OrderPublishServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void publish(Order order, List<Driver> drivers) {
        drivers.stream()
                .map(User::getUsername)
                .forEach(username -> simpMessagingTemplate
                        .convertAndSendToUser(username, "/queue/orders", OrderDto.map(order)));


    }

    @Override
    public void delete(Order order, List<Driver> drivers) {
        drivers.stream()
                .map(User::getUsername)
                .forEach(username -> simpMessagingTemplate
                        .convertAndSendToUser(username, "/queue/orders/events",
                                StompEvent.builder()
                                        .name("DELETE_ORDER")
                                        .payload(OrderDto.map(order))
                                        .build()
                        )
                );
    }

    @Override
    public void publishStartWaitingEvent(String customerUsername) {
        simpMessagingTemplate.convertAndSendToUser(customerUsername,
                "/queue/order/events",
                StompEvent.builder().name("START_WAITING").build());
    }

    @Override
    public void publishStopWaitingEvent(String customerUsername) {
        simpMessagingTemplate.convertAndSendToUser(customerUsername,
                "/queue/order/events",
                StompEvent.builder().name("STOP_WAITING").build());
    }

    @Override
    public void publishDoneOrderEvent(Order order) {
        simpMessagingTemplate.convertAndSendToUser(order.getCustomer().getUsername(),
                "/queue/order/events",
                StompEvent.<OrderDto>builder()
                        .name("ORDER_DONE")
                        .payload(OrderDto.map(order))
                        .build());
    }

    @Override
    public void publishCancelOrderEvent(Order order) {
        StompEvent<OrderDto> event = StompEvent.<OrderDto>builder()
                .name("ORDER_CANCELED")
                .payload(OrderDto.map(order))
                .build();

        simpMessagingTemplate.convertAndSendToUser(order.getCustomer().getUsername(),
                "/queue/order/events", event);

        Driver driver;
        if ((driver = order.getDriver()) != null) {
            simpMessagingTemplate.convertAndSendToUser(order.getDriver().getUsername(),
                    "/queue/order/events", event);
        }
    }
}
