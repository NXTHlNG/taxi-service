package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.Car;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CarDto {
    private final String name;
    private final String color;
    private final Integer year;
    private final String plate;

    public static CarDto map(Car car) {
        if (car == null) return null;
        return CarDto.builder()
                .name(car.getName())
                .color(car.getColor())
                .year(car.getYear())
                .plate(car.getPlate())
                .build();
    }
}
