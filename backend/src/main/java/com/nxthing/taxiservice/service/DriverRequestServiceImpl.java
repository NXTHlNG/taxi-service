package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.DriverRequest;
import com.nxthing.taxiservice.entity.DriverRequestStatus;
import com.nxthing.taxiservice.repository.DriverRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverRequestServiceImpl implements DriverRequestService {

    private final DriverRequestRepository driverRequestRepository;

    public DriverRequestServiceImpl(DriverRequestRepository driverRequestRepository) {
        this.driverRequestRepository = driverRequestRepository;
    }

    @Override
    public DriverRequest save(DriverRequest driverRequest) {
        if (driverRequestRepository.existsByEmail(driverRequest.getEmail()) ||
                driverRequestRepository.existsByLicense(driverRequest.getLicense())) {
            throw new RuntimeException();
        }

        return driverRequestRepository.save(driverRequest);
    }

    @Override
    public DriverRequest accept(Long id) {
        DriverRequest driverRequest = driverRequestRepository.findById(id).orElseThrow();

        if (driverRequest.getStatus() != DriverRequestStatus.PENDING) {
            throw new RuntimeException();
        }

        driverRequest.setStatus(DriverRequestStatus.ACCEPTED);

        return driverRequestRepository.save(driverRequest);
    }

    @Override
    public DriverRequest reject(Long id) {
        DriverRequest driverRequest = driverRequestRepository.findById(id).orElseThrow();

        if (driverRequest.getStatus() != DriverRequestStatus.PENDING) {
            throw new RuntimeException();
        }

        driverRequest.setStatus(DriverRequestStatus.REJECTED);

        return driverRequestRepository.save(driverRequest);
    }

    @Override
    public List<DriverRequest> getAll() {
        return driverRequestRepository.findAll();
    }

    @Override
    public List<DriverRequest> getAll(DriverRequestStatus status) {
        return driverRequestRepository.findAllByStatus(status);
    }
}
