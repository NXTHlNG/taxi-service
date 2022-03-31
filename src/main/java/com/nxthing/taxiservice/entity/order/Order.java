package com.nxthing.taxiservice.entity.order;

import com.nxthing.taxiservice.entity.District;
import com.nxthing.taxiservice.entity.car.Car;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Order {
    private Integer id;
    private String customer;
    private District fromAddress;
    private District toAddress;
    private List<Car> cars;
    private OrderStatus status;
    private Integer price;
}
