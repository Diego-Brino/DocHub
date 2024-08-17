package com.dochub.api;

import com.dochub.api.infra.env.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);

		app.addInitializers(new DotenvInitializer());
		app.run(args);
	}
}