package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User create(SignUpDto dto);

    User getByUserName(String username);
}
