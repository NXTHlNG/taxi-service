package com.nxthing.taxiservice.dto;

import com.nxthing.taxiservice.service.UserDetailsImpl;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@SuperBuilder
@ToString
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;

    public static UserDto map(UserDetailsImpl user) {
        return UserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
