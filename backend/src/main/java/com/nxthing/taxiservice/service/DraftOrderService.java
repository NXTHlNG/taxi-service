package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.PrepareOrderDto;
import com.nxthing.taxiservice.entity.Customer;
import com.nxthing.taxiservice.entity.DraftOrder;

import java.time.LocalDateTime;
import java.util.Map;

public interface DraftOrderService {
    DraftOrder create(PrepareOrderDto prepareOrderDto, Customer customer, Map<String, Double> prices);

    DraftOrder getById(Long id);

    void deleteExpired(LocalDateTime minusMinutes);
}
