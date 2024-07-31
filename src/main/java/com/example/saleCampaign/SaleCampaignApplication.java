package com.example.saleCampaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleCampaignApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleCampaignApplication.class, args);
	}

}
