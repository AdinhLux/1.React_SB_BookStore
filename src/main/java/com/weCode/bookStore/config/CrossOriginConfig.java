package com.weCode.bookStore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * To allow request from client on localhost and avoid 'Origin http://localhost:3000 is not allowed by Access-Control-Allow-Origin.'
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configure to accept requests from any port and ny domain
        registry.addMapping("/**");
    }
}
