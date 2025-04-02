package com.RPBank.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpBankApplication {

	public static void main(String[] args) {

		System.out.println("Application execution Started");
		SpringApplication.run(RpBankApplication.class, args);
		System.out.println("Application execution completed");
	}

}
