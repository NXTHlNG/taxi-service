package com.nxthing.taxiservice;

import com.nxthing.taxiservice.entity.Role;
import com.nxthing.taxiservice.entity.RoleName;
import com.nxthing.taxiservice.entity.User;
import com.nxthing.taxiservice.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class TaxiServiceApplicationTests {
    @Autowired
    CarRepository carRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DraftOrderRepository draftOrderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(passwordEncoder.encode("admin"));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).get());

        System.out.println(userRepository.findByUsername("artem813813@gmail.com").get().getPassword());

        System.out.println(passwordEncoder.encode("driver"));

//        userRepository.save(User.builder()
//                .username("admin")
//                .firstName("admin")
//                .lastName("admin")
//                .email("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles(roles)
//                .build());
    }

}
