package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.config.JwtUtils;
import com.nxthing.taxiservice.dto.AuthDto;
import com.nxthing.taxiservice.dto.SignInDto;
import com.nxthing.taxiservice.dto.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthDto signIn(SignInDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtHttp = jwtUtils.generateJwtToken(authentication, "http");
        String jwtWs = jwtUtils.generateJwtToken(authentication, "ws");

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return AuthDto.builder()
                .token(jwtHttp)
                .wsToken(jwtWs)
                .user(UserDto.map(user))
                .build();
    }
}
