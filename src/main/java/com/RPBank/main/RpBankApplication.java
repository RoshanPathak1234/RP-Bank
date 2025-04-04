package com.RPBank.main;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "RP Bank API",
				version = "1.0.0",
				description = "Comprehensive API documentation for RP Bank services.",
				contact = @Contact(
						name = "Roshan Pathak",
						email = "roshanpathak659@gmail.com",
						url = "https://github.com/RoshanPathak1234"
				),
				license = @License(
						name = "RP Bank License",
						url = "https://github.com/RoshanPathak1234/RP-Bank.git"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Detailed API Documentation for RP Bank",
				url = "https://github.com/RoshanPathak1234/RP-Bank.git"
		)
)
public class RpBankApplication {


	public static void main(String[] args) {
		System.out.println("Starting RP Bank Application...");
		SpringApplication.run(RpBankApplication.class, args);
		System.out.println("RP Bank Application is up and running!");
	}
}
