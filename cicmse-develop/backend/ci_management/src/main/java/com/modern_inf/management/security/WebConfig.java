package com.modern_inf.management.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final String FRONTEND_ORIGIN = "http://localhost:4200";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(FRONTEND_ORIGIN)
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*");
    }
}
