package com.tech_challenge.medical.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Medical Screening Processor API")
                        .version("1.0.0")
                        .description("API for the Medical Screening Processor MVP")
                        .contact(new Contact().name("Tech Challenge Team"))
                );
    }
}
