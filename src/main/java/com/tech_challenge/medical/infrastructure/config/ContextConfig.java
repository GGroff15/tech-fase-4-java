package com.tech_challenge.medical.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.genai.Client;

@Configuration
public class ContextConfig {

    @Bean
    Client genAiClient(GeminiProperties geminiProperties) {
        return Client.builder().apiKey(geminiProperties.apiKey()).build();
    }

}
