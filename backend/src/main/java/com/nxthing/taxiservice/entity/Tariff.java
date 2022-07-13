package com.nxthing.taxiservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Tariffs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tariff {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tariff_generator")
    @SequenceGenerator(name="tariff_generator", sequenceName = "tariffs_seq", allocationSize = 1)
    private Long id;

    @Column(name = "rank")
    private Long rank;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "min_price")
    private Double minPrice;

    @Column(name = "minute_price")
    private Double minutePrice;

    @Column(name = "kilometer_price")
    private Double kilometerPrice;

    @Column(name = "waiting_price")
    private Double waitingPrice;

    @Column(name = "free_waiting")
    private Integer freeWaiting;

    @Column(name = "commission")
    private Double commission;
}
