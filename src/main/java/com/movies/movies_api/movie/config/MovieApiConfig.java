package com.movies.movies_api.movie.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class MovieApiConfig {

    @Bean
    public RestTemplate movieApiTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(20))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();
    }
}