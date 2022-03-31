package com.nxthing.taxiservice.entity.car;

import com.nxthing.taxiservice.entity.District;
import lombok.Data;

@Data
public abstract class Car {
    private final int capacity;
//    private CarStatus status = CarStatus.FREE;
//    private District location;

    public Car(int capacity) {
        this.capacity = capacity;
    }
}
