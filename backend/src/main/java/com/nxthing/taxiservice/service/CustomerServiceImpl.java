package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.Customer;
import com.nxthing.taxiservice.entity.OrderStatus;
import com.nxthing.taxiservice.entity.Role;
import com.nxthing.taxiservice.entity.RoleName;
import com.nxthing.taxiservice.repository.CustomerRepository;
import com.nxthing.taxiservice.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               PasswordEncoder passwordEncoder,
                               RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Customer getByUserName(String username) {
        return customerRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public Customer create(SignUpDto dto) {
        Customer user = Customer.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        RoleName requestRole = dto.getRole();
        Set<Role> roles = new HashSet<>();

        if (requestRole == null) {
            Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Role CUSTOMER is not found"));
            roles.add(customerRole);
        } else {
            roles.add(roleRepository.findByName(requestRole).orElseThrow());
        }

        user.setRoles(roles);

        return customerRepository.save(user);
    }

    @Override
    public boolean hasActiveOrders(Customer customer) {
        return customer.getOrders().stream().anyMatch(order -> order.getStatus() != OrderStatus.DONE &&
                order.getStatus() != OrderStatus.CANCELLED);
    }
}
