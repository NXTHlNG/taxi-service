package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.Driver;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverDto {
    private String firstName;
    private String lastName;
    private Double rating;
    private String geoPosition;
    private CarDto car;
    private Double balance;
    private String tariff;

    public static DriverDto map(Driver driver) {
        return DriverDto.builder()
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .rating(driver.getRating())
                .geoPosition(driver.getFirstName())
                .car(CarDto.map(driver.getCar()))
                .balance(driver.getBalance())
                .tariff(driver.getTariff() != null ? driver.getTariff().getName() : null)
                .build();
    }
}
