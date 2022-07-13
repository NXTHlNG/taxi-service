package com.nxthing.taxiservice.entity;

public enum PaymentMethod {
    CARD(1d),
    CASH(1.04d);

    private final double paymentMethodMultiplier;

    PaymentMethod(double paymentMethodMultiplier) {
        this.paymentMethodMultiplier = paymentMethodMultiplier;
    }

    public double getPaymentMethodMultiplier() {
        return paymentMethodMultiplier;
    }
}
