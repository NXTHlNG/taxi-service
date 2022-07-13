//package com.nxthing.taxiservice;
//
//import com.nxthing.taxiservice.entity.Driver;
//import com.nxthing.taxiservice.repository.DriverRepository;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Tst {
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    private final DriverRepository driverRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    public Tst(SimpMessagingTemplate simpMessagingTemplate, DriverRepository driverRepository, PasswordEncoder passwordEncoder) {
//        this.simpMessagingTemplate = simpMessagingTemplate;
//        this.driverRepository = driverRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Scheduled(fixedDelay = 10000)
//    public void tst() {
//        System.out.println("111");
//        simpMessagingTemplate.convertAndSendToUser("s","/queue/reply", "asd");
//        simpMessagingTemplate.convertAndSend("/driver/orders", "all");
//    }
//}
