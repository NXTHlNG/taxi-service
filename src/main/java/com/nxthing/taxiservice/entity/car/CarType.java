package com.nxthing.taxiservice.entity.car;

public enum CarType {
    PASSENGER(4),
    FREIGHT(2000);

    public final int capacity;

    CarType(int capacity) {
        this.capacity = capacity;
    }
}
