package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.SignUpDto;
import com.nxthing.taxiservice.entity.Customer;
import com.nxthing.taxiservice.entity.User;

public interface CustomerService {
    Customer getByUserName(String userName);

    Customer create(SignUpDto dto);

    boolean hasActiveOrders(Customer customer);
}
