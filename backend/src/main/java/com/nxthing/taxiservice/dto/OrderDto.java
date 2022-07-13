package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.Order;
import com.nxthing.taxiservice.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@ToString
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private String origin;
    private String destination;
    private Double distance;
    private Double duration;
    private OrderStatus status;
    private Double price;
    private Double waitingPrice;
    private Double commission;
    private String tariff;
    private String driver;
    private String customer;
    private Long waitingTime;
    private Long tripDuration;

    public static OrderDto map(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .origin(order.getOrigin())
                .destination(order.getDestination())
                .distance(order.getDistance())
                .duration(order.getDuration())
                .status(order.getStatus())
                .price(order.getPrice())
                .tariff(order.getTariff() != null ? order.getTariff().getName() : null)
                .waitingPrice(order.getWaitingPrice())
                .waitingTime(getWaitingTime(order))
                .tripDuration(getTripDuration(order))
                .commission(order.getCommission())
                .build();
    }

    private static long getWaitingTime(Order order) {
        return order.getStartWaitTime() != null && order.getStopWaitTime() != null ?
                ChronoUnit.MINUTES.between(order.getStartWaitTime(), order.getStopWaitTime()) : 0;
    }

    private static long getTripDuration(Order order) {
        return order.getStopWaitTime() != null && order.getEndTime() != null ?
                ChronoUnit.MINUTES.between(order.getStopWaitTime(), order.getEndTime()) : 0;
    }
}
