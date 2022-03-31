package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.District;

public interface OrderPriceCalculationService {
    int calculatePrice(District from, District to);
}
