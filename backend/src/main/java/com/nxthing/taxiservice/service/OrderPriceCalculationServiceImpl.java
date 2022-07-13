package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.Order;
import com.nxthing.taxiservice.entity.PaymentMethod;
import com.nxthing.taxiservice.entity.Tariff;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderPriceCalculationServiceImpl implements OrderPriceCalculationService {
    @Override
    public double calculatePrice(String origin, String destination, double distance, double duration, PaymentMethod paymentMethod, Tariff tariff) {
        double price = (distance * tariff.getKilometerPrice() + duration * tariff.getMinutePrice()) * paymentMethod.getPaymentMethodMultiplier();

        return Math.max(Math.round(price), tariff.getMinPrice());
    }

    @Override
    public Map<String, Double> calculatePrices(String origin, String destination, double distance, double duration, PaymentMethod paymentMethod, List<Tariff> tariffs) {
        return tariffs.stream()
                .collect(Collectors.toMap(Tariff::getName,
                        tariff -> calculatePrice(origin, destination, distance, duration, paymentMethod, tariff)));
    }

    @Override
    public Double calculateWaitingPrice(Order order) {
        long waitingTime = ChronoUnit.MINUTES.between(order.getStartWaitTime(), order.getStopWaitTime());

        return waitingTime < order.getTariff().getFreeWaiting() ? 0 :
                waitingTime * order.getTariff().getWaitingPrice();
    }

    @Override
    public Double calculateCommission(Order order) {
        return (order.getPrice() + order.getWaitingPrice()) * order.getTariff().getCommission() / 100;
    }

    @Override
    public Order calculateTotalPrice(Order order) {
        order.setWaitingPrice(calculateWaitingPrice(order));
        order.setTotalPrice(order.getPrice() + order.getWaitingPrice());
        order.setCommission(calculateCommission(order));
        return order;
    }
}
