package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.Role;
import com.nxthing.taxiservice.entity.RoleName;
import com.nxthing.taxiservice.entity.User;
import com.nxthing.taxiservice.repository.RoleRepository;
import com.nxthing.taxiservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return UserDetailsImpl.map(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User create(SignUpDto dto) {
        User user = User.builder()
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

        return userRepository.save(user);
    }

    @Override
    public User getByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }


}
