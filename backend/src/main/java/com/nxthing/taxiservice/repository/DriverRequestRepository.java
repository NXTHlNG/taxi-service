package com.nxthing.taxiservice.repository;

import com.nxthing.taxiservice.entity.DriverRequest;
import com.nxthing.taxiservice.entity.DriverRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRequestRepository extends JpaRepository<DriverRequest, Long> {
    boolean existsByEmail(String email);

    boolean existsByLicense(String license);

    List<DriverRequest> findAllByStatus(DriverRequestStatus status);
}