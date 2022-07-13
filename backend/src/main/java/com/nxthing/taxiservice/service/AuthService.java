package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.AuthDto;
import com.nxthing.taxiservice.dto.SignInDto;

public interface AuthService {
    AuthDto signIn(SignInDto dto);
}
