package com.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix("/api/v1",
        c -> {
          // Only apply to RestControllers in our application package
          if (!c.isAnnotationPresent(org.springframework.web.bind.annotation.RestController.class)) {
            return false;
          }
          // Only apply to our app controllers, exclude SpringDoc
          String packageName = c.getPackageName();
          return packageName.startsWith("com.app")
              && !packageName.contains("springdoc");
        });
  }
}
