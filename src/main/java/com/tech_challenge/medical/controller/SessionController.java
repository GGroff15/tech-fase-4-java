package com.tech_challenge.medical.controller;

import com.tech_challenge.medical.application.SessionService;
import com.tech_challenge.medical.controller.dto.SessionCreatedResponse;
import com.tech_challenge.medical.domain.session.CorrelationId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/sessions")
@Tag(name = "Sessions", description = "Session lifecycle and creation endpoints")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    @Operation(summary = "Create session", description = "Creates a new clinical session and returns a correlation id")
    @ApiResponse(responseCode = "201", description = "Session created")
    public ResponseEntity<SessionCreatedResponse> createSession() {
        CorrelationId correlationId = sessionService.createSession();
        SessionCreatedResponse response = SessionCreatedResponse.of(correlationId.asString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
