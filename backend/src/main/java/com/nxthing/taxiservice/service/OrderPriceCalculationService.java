package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.Order;
import com.nxthing.taxiservice.entity.PaymentMethod;
import com.nxthing.taxiservice.entity.Tariff;

import java.util.List;
import java.util.Map;

public interface OrderPriceCalculationService {

    double calculatePrice(String origin, String destination, double distance, double duration, PaymentMethod paymentMethod, Tariff tariff);

    Double calculateCommission(Order order);

    Map<String, Double> calculatePrices(String origin, String destination, double distance, double duration, PaymentMethod paymentMethod, List<Tariff> tariffs);

    Double calculateWaitingPrice(Order order);

    Order calculateTotalPrice(Order order);
}
