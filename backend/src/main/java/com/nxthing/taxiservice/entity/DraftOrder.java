package com.nxthing.taxiservice.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "draftorders")
@TypeDef(name = "json", typeClass = JsonType.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DraftOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "draftOrders_generator")
    @SequenceGenerator(name="draftOrders_generator", sequenceName = "draftorders_seq", allocationSize = 1)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Type(type = "json")
    @Column(name = "prices")
    private Map<String, Double> prices;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
