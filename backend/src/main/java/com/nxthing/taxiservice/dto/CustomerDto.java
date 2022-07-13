package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class CustomerDto extends UserDto {
    private List<Order> orders;
}
