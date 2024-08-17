package com.dochub.api.infra.env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final Dotenv dotenv = Dotenv
            .configure()
            .directory("./backend/")
            .load();

        final ConfigurableEnvironment environment = applicationContext.getEnvironment();

        dotenv.entries().forEach(entry -> {
            environment.getSystemProperties().put(entry.getKey(), entry.getValue());
        });
    }
}