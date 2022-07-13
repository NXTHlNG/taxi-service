package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.DriverDto;
import com.nxthing.taxiservice.entity.Driver;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverPublishServiceImpl implements DriverPublishService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public DriverPublishServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void publish(Driver driver, String customerUsername) {
        simpMessagingTemplate.convertAndSendToUser(customerUsername, "/queue/drivers", DriverDto.map(driver));
    }


}
