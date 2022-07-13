package com.nxthing.taxiservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Drivers")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Driver extends User {
    @Column(name = "rating")
    private double rating;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "geo_position")
    private String geoPosition;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @OneToOne(mappedBy = "driver")
    private Car car;

    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    private List<Order> orders;


    public Driver(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
    }
}
