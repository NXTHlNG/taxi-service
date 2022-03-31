package com.nxthing.taxiservice.entity.car;

import org.springframework.stereotype.Component;

public class CarFactory {
    public static Car createCar(CarType type) {
        switch (type) {
            case PASSENGER:
                return new PassengerCar();
            case FREIGHT:
                return new FreightCar();
            default:
                return null;
        }
    }
}
