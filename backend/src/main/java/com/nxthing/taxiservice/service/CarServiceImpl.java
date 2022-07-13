package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
}
