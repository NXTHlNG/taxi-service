package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.DriverRequest;
import com.nxthing.taxiservice.entity.DriverRequestStatus;

import java.util.List;

public interface DriverRequestService {
    DriverRequest save(DriverRequest build);

    DriverRequest accept(Long id);

    DriverRequest reject(Long id);

    List<DriverRequest> getAll();

    List<DriverRequest> getAll(DriverRequestStatus status);
}
