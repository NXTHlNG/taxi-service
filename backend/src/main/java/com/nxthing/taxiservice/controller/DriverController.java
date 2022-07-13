package com.nxthing.taxiservice.controller;

import com.nxthing.taxiservice.dto.DriverDto;
import com.nxthing.taxiservice.dto.DriverRequestDto;
import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.*;
import com.nxthing.taxiservice.service.DriverRequestService;
import com.nxthing.taxiservice.service.DriverService;
import com.nxthing.taxiservice.service.UserDetailsImpl;
import com.nxthing.taxiservice.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin("*")
public class DriverController {
    private final DriverService driverService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final DriverRequestService driverRequestService;

    public DriverController(DriverService driverService,
                            UserService userService,
                            PasswordEncoder passwordEncoder,
                            DriverRequestService driverRequestService) {
        this.driverService = driverService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.driverRequestService = driverRequestService;
    }

    @PostMapping("/set-status")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public DriverDto setStatus(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam DriverStatus status) {
        Driver driver = driverService.getByUsername(user.getUsername());
        driver.setStatus(status);

        return DriverDto.map(driverService.save(driver));
    }

    @PostMapping("/send-request")
    @Transactional
    public DriverRequest sendRequest(@RequestBody DriverRequestDto driverRequestDto) {
        if (userService.existsByEmail(driverRequestDto.getEmail())) {
            throw new RuntimeException();
        }

        return driverRequestService.save(DriverRequest.builder()
                .date(LocalDateTime.now())
                .firstName(driverRequestDto.getFirstName())
                .lastName(driverRequestDto.getLastName())
                .license(driverRequestDto.getLicense())
                .email(driverRequestDto.getEmail())
                .firstName(driverRequestDto.getFirstName())
                .status(DriverRequestStatus.PENDING)
                .build()
        );
    }

    @PostMapping("/accept-request")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public DriverDto acceptRequest(@RequestParam Long id) {
        DriverRequest driverRequest = driverRequestService.accept(id);
        return DriverDto.map(driverService.create(
                SignUpDto.builder()
                        .username(driverRequest.getEmail())
                        .firstName(driverRequest.getFirstName())
                        .lastName(driverRequest.getLastName())
                        .password("driver")
                        .role(RoleName.ROLE_DRIVER)
                        .email(driverRequest.getEmail())
                        .build())
        );
    }

    @PostMapping("/reject-request")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public DriverRequest rejectRequest(@RequestParam Long id) {
        return driverRequestService.reject(id);
    }

    @GetMapping("/get-all-requests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public List<DriverRequest> getAll(@RequestParam(required = false) DriverRequestStatus status) {
        if (status != null) {
            return driverRequestService.getAll(status);
        } else {
            return driverRequestService.getAll();
        }
    }
}
