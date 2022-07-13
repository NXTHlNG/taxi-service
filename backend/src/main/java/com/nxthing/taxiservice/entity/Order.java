package com.nxthing.taxiservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "order_generator", sequenceName = "orders_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "start_wait_time")
    private LocalDateTime startWaitTime;

    @Column(name = "stop_wait_time")
    private LocalDateTime stopWaitTime;

    @Column(name = "price")
    private Double price;

    @Column(name = "waiting_price")
    private Double waitingPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Version
    @Column(name = "version", columnDefinition = "biginteger default 0")
    private Long version = 0L;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
