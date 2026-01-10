package com.tech_challenge.medical.infraestructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    YoloV8Client yoloV8Client(@Value("${yolov8.service.url:http://localhost:8000}") String yolov8ServiceUrl) {
        RestClient restClient = RestClient.create(yolov8ServiceUrl);
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(adapter)
                .customArgumentResolver(new ProcessVideoRequestArgumentResolver())
                .build();
        return factory.createClient(YoloV8Client.class);
    }

}
