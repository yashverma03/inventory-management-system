package com.app.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DotEnvConfig implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    ConfigurableEnvironment environment = event.getEnvironment();

    // Check if .env file exists
    File envFile = new File("./.env");
    if (!envFile.exists()) {
      throw new RuntimeException(".env file not found in project root directory. Please create a .env file.");
    }

    // Load .env file
    Dotenv dotenv = Dotenv.configure()
        .directory("./")
        .load();

    if (dotenv.entries().isEmpty()) {
      throw new RuntimeException(".env file is empty or contains no valid entries.");
    }

    // Convert dotenv entries to Spring properties
    Map<String, Object> envProperties = new HashMap<>();
    dotenv.entries().forEach(entry -> {
      envProperties.put(entry.getKey(), entry.getValue());
    });

    // Add as highest priority property source (before application.properties)
    MutablePropertySources propertySources = environment.getPropertySources();
    propertySources.addFirst(new MapPropertySource("dotenv", envProperties));
  }
}
