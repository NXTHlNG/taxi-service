package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.Tariff;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class TariffDto {
    private String name;
    private Double minPrice;
    private Double minutePrice;
    private Double kilometerPrice;
    private Double waitingPrice;
    private Double freeWaiting;
    private Double commission;

    public static TariffDto map(Tariff tariff) {
        return TariffDto.builder()
                .name(tariff.getName())
                .minPrice(tariff.getMinPrice())
                .minutePrice(tariff.getMinPrice())
                .kilometerPrice(tariff.getMinPrice())
                .waitingPrice(tariff.getMinPrice())
                .freeWaiting(tariff.getMinPrice())
                .commission(tariff.getMinPrice())
                .build();
    }

}
