package com.nxthing.taxiservice.entity;

public enum District {
    LENINSKIY(2),
    VOLZHSKIY(1),
    KIROVSKIY(0),
    OKTYABRSKIY(-1),
    ZAVODSKOY(-2);

    public final int value;

    District(int value) {
        this.value = value;
    }
}
