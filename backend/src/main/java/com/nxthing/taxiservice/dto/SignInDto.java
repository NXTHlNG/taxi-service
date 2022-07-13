package com.nxthing.taxiservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SignInDto {
    String username;
    String password;
}
