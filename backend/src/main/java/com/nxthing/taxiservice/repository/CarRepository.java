package com.nxthing.taxiservice.repository;

import com.nxthing.taxiservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CarRepository extends JpaRepository<Car, Long> {
}