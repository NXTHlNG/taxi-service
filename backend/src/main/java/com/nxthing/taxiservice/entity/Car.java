package com.nxthing.taxiservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Cars")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_generator")
    @SequenceGenerator(name="car_generator", sequenceName = "cars_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "year")
    private Integer year;

    @Column(name = "plate", unique = true)
    private String plate;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
