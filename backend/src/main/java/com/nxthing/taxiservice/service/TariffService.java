package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.Tariff;

import java.util.List;

public interface TariffService {
    Tariff getByName(String name);

    List<Tariff> getAll();
}
