package com.tech_challenge.medical.infrastructure.security;

import com.tech_challenge.medical.domain.exception.InvalidApiKeyException;
import com.tech_challenge.medical.infrastructure.config.TriageProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    
    private static final String API_KEY_HEADER = "X-API-Key";
    private static final String EVENTS_PATH = "/events";

    private final TriageProperties properties;

    public ApiKeyFilter(TriageProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        if (shouldValidateApiKey(request)) {
            validateApiKey(request);
        }
        
        filterChain.doFilter(request, response);
    }

    private boolean shouldValidateApiKey(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith(EVENTS_PATH);
    }

    private void validateApiKey(HttpServletRequest request) {
        String providedKey = request.getHeader(API_KEY_HEADER);
        String configuredKey = properties.security().apiKey();
        
        if (providedKey == null || !providedKey.equals(configuredKey)) {
            throw new InvalidApiKeyException("Missing or invalid API key");
        }
    }
}
