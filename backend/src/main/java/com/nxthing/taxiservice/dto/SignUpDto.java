package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.entity.RoleName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class SignUpDto {
    String username;
    String firstName;
    String lastName;
    String password;
    String email;
    RoleName role;
}
