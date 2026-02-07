package com.tech_challenge.medical.infrastructure.security;

import com.tech_challenge.medical.domain.exception.InvalidApiKeyException;
import com.tech_challenge.medical.infrastructure.config.TriageProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApiKeyFilter tests")
class ApiKeyFilterTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Mock
    TriageProperties properties;

    @Mock
    TriageProperties.Security securityConfig;

    @InjectMocks
    private ApiKeyFilter filter;

    private static final String VALID_API_KEY = "test-api-key-123";

    @BeforeEach
    void setUp() {

    }

    @Nested
    @DisplayName("when filtering events endpoint")
    class EventsEndpointTests {

        @Test
        @DisplayName("allows request with valid API key")
        void allowsValidApiKey() throws Exception {
            // Given
            when(properties.security()).thenReturn(securityConfig);
            when(securityConfig.apiKey()).thenReturn(VALID_API_KEY);
            when(request.getRequestURI()).thenReturn("/events/emotion");
            when(request.getHeader("X-API-Key")).thenReturn(VALID_API_KEY);

            // When & Then
            assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
            verify(filterChain).doFilter(request, response);
        }

        @Test
        @DisplayName("rejects request with missing API key")
        void rejectsMissingApiKey() {
            // Given
            when(properties.security()).thenReturn(securityConfig);
            when(securityConfig.apiKey()).thenReturn(VALID_API_KEY);
            when(request.getRequestURI()).thenReturn("/events/emotion");
            when(request.getHeader("X-API-Key")).thenReturn(null);

            // When & Then
            assertThrows(InvalidApiKeyException.class, () -> filter.doFilterInternal(request, response, filterChain));
        }

        @Test
        @DisplayName("rejects request with invalid API key")
        void rejectsInvalidApiKey() {
            // Given
            when(properties.security()).thenReturn(securityConfig);
            when(securityConfig.apiKey()).thenReturn(VALID_API_KEY);
            when(request.getRequestURI()).thenReturn("/events/emotion");
            when(request.getHeader("X-API-Key")).thenReturn("wrong-key");

            // When & Then
            assertThrows(InvalidApiKeyException.class, () -> filter.doFilterInternal(request, response, filterChain));
        }

        @Test
        @DisplayName("validates all events sub-paths")
        void validatesAllEventsSubPaths() {
            // Given
            when(properties.security()).thenReturn(securityConfig);
            when(securityConfig.apiKey()).thenReturn(VALID_API_KEY);
            when(request.getRequestURI()).thenReturn("/events/transcript");
            when(request.getHeader("X-API-Key")).thenReturn(VALID_API_KEY);

            // When & Then
            assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
        }
    }

    @Nested
    @DisplayName("when filtering other endpoints")
    class OtherEndpointTests {

        @Test
        @DisplayName("allows sessions endpoint without API key")
        void allowsSessionsEndpoint() throws Exception {
            // Given
            when(request.getRequestURI()).thenReturn("/sessions");

            // When & Then
            assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
            verify(filterChain).doFilter(request, response);
        }

        @Test
        @DisplayName("allows forms endpoint without API key")
        void allowsFormsEndpoint() throws Exception {
            // Given
            when(request.getRequestURI()).thenReturn("/forms/123");

            // When & Then
            assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
            verify(filterChain).doFilter(request, response);
        }

        @Test
        @DisplayName("does not check API key for unprotected paths")
        void doesNotCheckForUnprotectedPaths() throws Exception {
            // Given
            when(request.getRequestURI()).thenReturn("/health");

            // When
            filter.doFilterInternal(request, response, filterChain);

            // Then
            verify(request, never()).getHeader("X-API-Key");
            verify(filterChain).doFilter(request, response);
        }
    }
}
