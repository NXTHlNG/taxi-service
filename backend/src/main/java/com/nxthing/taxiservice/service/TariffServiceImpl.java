package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.Tariff;
import com.nxthing.taxiservice.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;

    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Tariff getByName(String name) {
        return tariffRepository.findByName(name).orElseThrow();
    }

    @Override
    public List<Tariff> getAll() {
        return tariffRepository.findAllByOrderByRankAsc();
    }
}
