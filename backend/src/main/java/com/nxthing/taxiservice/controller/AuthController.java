package com.nxthing.taxiservice.controller;

import com.nxthing.taxiservice.dto.AuthDto;
import com.nxthing.taxiservice.dto.SignInDto;
import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.User;
import com.nxthing.taxiservice.service.AuthService;
import com.nxthing.taxiservice.service.CustomerService;
import com.nxthing.taxiservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;

    private final CustomerService customerService;

    private final AuthService authService;

    public AuthController(final UserService userService, CustomerService customerService, AuthService authService) {
        this.userService = userService;
        this.customerService = customerService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public User register(@RequestBody SignUpDto dto) {
        if (userService.existsByUsername(dto.getUsername()) || userService.existsByEmail(dto.getEmail())) {
            throw new RuntimeException();
        }

        return customerService.create(dto);
    }

    @PostMapping("/signin")
    public AuthDto login(@RequestBody SignInDto dto) {
        return authService.signIn(dto);
    }

//    @GetMapping("/activate/{code}")
//    public boolean activate(@PathVariable String code) {
//        return userService.activateUser(code);
//    }
}