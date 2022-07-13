package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.*;
import com.nxthing.taxiservice.repository.DriverRepository;
import com.nxthing.taxiservice.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DriverServiceImpl implements DriverService{
    private final DriverRepository driverRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public DriverServiceImpl(DriverRepository driverRepository,
                             PasswordEncoder passwordEncoder,
                             RoleRepository roleRepository) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    //TODO: refactor this shit
    @Override
    @Transactional
    public Driver create(SignUpDto dto) {
        Driver user = Driver.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        RoleName requestRole = dto.getRole();
        Set<Role> roles = new HashSet<>();

        if (requestRole == null) {
            Role customerRole = roleRepository.findByName(RoleName.ROLE_DRIVER)
                    .orElseThrow(() -> new RuntimeException("Role CUSTOMER is not found"));
            roles.add(customerRole);
        } else {
            roles.add(roleRepository.findByName(requestRole).orElseThrow());
        }

        user.setRoles(roles);

        return driverRepository.save(user);
    }

    @Override
    public Driver getByUsername(String username) {
        return driverRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public List<Driver> getActive() {
        return driverRepository.findAllByStatus(DriverStatus.LOOKING_FOR_ORDER);
    }

    @Override
    public List<Driver> getActiveWithTariff(Tariff tariff) {
        return driverRepository.findAllByStatusAndTariffName(DriverStatus.LOOKING_FOR_ORDER, tariff.getName());
    }

    @Override
    @Transactional
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver addBalance(Driver driver, double money) {
        Double balance;

        if ((balance = driver.getBalance()) == null) {
            balance = 0d;
        }

        driver.setBalance(balance + money);

        return driverRepository.save(driver);
    }
}
