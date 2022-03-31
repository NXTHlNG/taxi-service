package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.car.Car;
import com.nxthing.taxiservice.entity.car.CarType;

import java.util.List;

public interface CarService {
    List<Car> getCars(CarType type, int capacity /*District fromAddress, District toAddress*/);
}