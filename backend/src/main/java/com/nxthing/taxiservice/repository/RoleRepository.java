package com.nxthing.taxiservice.repository;

import com.nxthing.taxiservice.entity.Role;
import com.nxthing.taxiservice.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}