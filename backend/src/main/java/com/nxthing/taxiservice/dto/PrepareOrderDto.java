package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.PaymentMethod;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PrepareOrderDto {
    private Long id;
    private String origin;
    private String destination;
    private Double distance;
    private Double duration;
    private PaymentMethod paymentMethod;
}
