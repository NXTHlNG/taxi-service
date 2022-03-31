package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.District;
import org.springframework.stereotype.Service;

@Service
public class OrderPriceCalculationServiceImpl implements OrderPriceCalculationService {
    @Override
    public int calculatePrice(District from, District to) {
        return Math.abs(from.value - to.value) * 1000;
    }
}
