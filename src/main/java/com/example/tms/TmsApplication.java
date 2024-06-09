package com.example.tms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("DATABASE_URL: " + System.getenv("DATABASE_URL"));
			System.out.println("DATABASE_USERNAME: " + System.getenv("DATABASE_USERNAME"));
			System.out.println("DATABASE_PASSWORD: " + System.getenv("DATABASE_PASSWORD"));
		};
	}

}
