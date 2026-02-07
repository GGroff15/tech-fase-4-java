package com.tech_challenge.medical.infrastructure.web;

import com.tech_challenge.medical.domain.exception.InvalidApiKeyException;
import com.tech_challenge.medical.domain.exception.SessionAlreadyClosedException;
import com.tech_challenge.medical.domain.exception.SessionExpiredException;
import com.tech_challenge.medical.domain.exception.SessionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSessionNotFound(SessionNotFoundException ex) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Session not found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleSessionExpired(SessionExpiredException ex) {
        return buildErrorResponse(
                HttpStatus.GONE,
                "Session expired",
                ex.getMessage()
        );
    }

    @ExceptionHandler(SessionAlreadyClosedException.class)
    public ResponseEntity<Map<String, Object>> handleSessionAlreadyClosed(SessionAlreadyClosedException ex) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Session already closed",
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidApiKey(InvalidApiKeyException ex) {
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Invalid API key",
                ex.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid request",
                ex.getMessage()
        );
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status,
            String error,
            String message
    ) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", error,
                "message", message
        );
        return ResponseEntity.status(status).body(body);
    }
}
