package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.Customer;
import com.nxthing.taxiservice.entity.DraftOrder;
import com.nxthing.taxiservice.entity.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
public class DraftOrderDto {
    private Long id;

    private LocalDateTime date;

    private String origin;

    private String destination;

    private Double distance;

    private Double duration;

    private PaymentMethod paymentMethod;

    private Map<String, Double> prices;


    public static DraftOrderDto map(DraftOrder draftOrder) {
        return DraftOrderDto.builder()
                .id(draftOrder.getId())
                .date(draftOrder.getDate())
                .origin(draftOrder.getOrigin())
                .destination(draftOrder.getDestination())
                .distance(draftOrder.getDistance())
                .duration(draftOrder.getDuration())
                .prices(draftOrder.getPrices())
                .paymentMethod(draftOrder.getPaymentMethod())
                .build();
    }
}
