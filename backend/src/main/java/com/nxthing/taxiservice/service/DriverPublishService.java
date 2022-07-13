package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.entity.Customer;
import com.nxthing.taxiservice.entity.Driver;
import com.nxthing.taxiservice.entity.Order;

public interface DriverPublishService {
    void publish(Driver driver, String customerUsername);

}
