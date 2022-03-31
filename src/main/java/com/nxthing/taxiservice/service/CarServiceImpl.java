package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.car.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarServiceImpl implements CarService {

    @Override
    public List<Car> getCars(CarType type, int capacity /*District fromAddress, District toAddress*/) {
        int numberOfCars = (int) Math.ceil((double)capacity/type.capacity);

        return Stream.generate(() -> CarFactory.createCar(type)).limit(numberOfCars).collect(Collectors.toList());

        //TODO: Get Cars from Database
        //TODO: Implementation of Car Status

//        return carRepository.findCarsByStatusAndType(CarStatus.FREE, type)
//                .stream()
//                //.sorted(Comparator.comparingInt(car -> car.getLocation().value - fromAddress.value))
//                .limit(numberOfCars)
//                //.peek(car -> car.setStatus(CarStatus.ON_THE_WAY))
//                .collect(Collectors.toList());
    }
}
