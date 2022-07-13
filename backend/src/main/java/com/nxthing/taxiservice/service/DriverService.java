package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.Driver;
import com.nxthing.taxiservice.entity.Tariff;

import java.util.List;

public interface DriverService {

    Driver create(SignUpDto dto);

    List<Driver> getActive();

    Driver getByUsername(String username);

    Driver save(Driver driver);

    Driver addBalance(Driver driver, double v);

    List<Driver> getActiveWithTariff(Tariff tariff);
}
