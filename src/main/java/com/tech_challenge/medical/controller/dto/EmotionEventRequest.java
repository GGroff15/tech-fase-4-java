package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Emotion event produced by audio analysis")
public record EmotionEventRequest(
        @NotBlank @Schema(description = "Emotion label", example = "happy") String emotion,
        @NotNull @Schema(description = "Confidence for the detected emotion", example = "0.92") Double confidence,
        @NotBlank @Schema(description = "ISO-8601 timestamp of the event", example = "2025-01-01T12:00:00Z") String timestamp
) {
}
