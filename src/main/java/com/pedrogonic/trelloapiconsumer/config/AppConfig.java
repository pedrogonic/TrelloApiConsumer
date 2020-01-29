package com.pedrogonic.trelloapiconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:trello-api.properties")
@Configuration
public class AppConfig {

    public static String API_KEY;
    public static String API_TOKEN;

    @Value("${api.key}")
    public void apiKey(String key) {
        this.API_KEY = key;
    }

    @Value("${api.token}")
    public void apiToken(String token) {
        this.API_TOKEN = token;
    }

}
