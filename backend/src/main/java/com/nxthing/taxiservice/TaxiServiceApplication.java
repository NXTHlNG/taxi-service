package com.nxthing.taxiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaxiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiServiceApplication.class, args);
	}

}
