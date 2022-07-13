package com.nxthing.taxiservice.controller;

import com.nxthing.taxiservice.entity.Tariff;
import com.nxthing.taxiservice.service.TariffService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
@CrossOrigin("*")
public class TariffController {
    private final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping("/")
    public List<Tariff> getTariffs() {
        return tariffService.getAll();
    }
}
