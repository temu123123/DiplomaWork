package com.diploma.riskprediction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RiskPredictionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskPredictionApplication.class, args);
	}

}
