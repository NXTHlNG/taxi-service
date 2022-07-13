package com.nxthing.taxiservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverRequestDto {
    private String firstName;
    private String lastName;
    private String license;
    private String email;
}
