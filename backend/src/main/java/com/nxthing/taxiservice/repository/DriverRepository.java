package com.nxthing.taxiservice.repository;

import com.nxthing.taxiservice.entity.Driver;
import com.nxthing.taxiservice.entity.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByStatus(DriverStatus lookingForOrder);

    Optional<Driver> findByUsername(String username);

    List<Driver> findAllByStatusAndTariffName(DriverStatus status, String tariffName);
}