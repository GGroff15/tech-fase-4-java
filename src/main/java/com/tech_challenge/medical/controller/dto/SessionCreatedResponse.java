package com.tech_challenge.medical.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned when a session is created")
public record SessionCreatedResponse(
        @Schema(description = "Correlation id for the created session", example = "abc-123") String correlationId
) {
    public static SessionCreatedResponse of(String correlationId) {
        return new SessionCreatedResponse(correlationId);
    }
}
