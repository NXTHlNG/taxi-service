package com.nxthing.taxiservice.repository;

import com.nxthing.taxiservice.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findByName(String name);

    List<Tariff> findAllByOrderByRankAsc();
}