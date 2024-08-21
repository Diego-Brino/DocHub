package com.dochub.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}
}