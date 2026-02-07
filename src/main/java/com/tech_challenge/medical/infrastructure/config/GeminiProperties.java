package com.tech_challenge.medical.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gemini")
public class GeminiProperties {

    private String apiKey;
    private String systemPrompt;
    private String outputSchema;
    private String model;

    public String apiKey() {
        return apiKey;
    }

    public String systemPrompt() {
        return systemPrompt;
    }

    public String outputSchema() {
        return outputSchema;
    }

    public String model() {
        return model;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    public void setOutputSchema(String outputSchema) {
        this.outputSchema = outputSchema;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
    
}
