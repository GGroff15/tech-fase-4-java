package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Transcript chunk produced by speech-to-text processing")
public record TranscriptChunkRequest(
        @NotBlank @Schema(description = "Transcript text") String text,
        @NotNull @Schema(description = "Confidence for the transcript chunk", example = "0.9") Double confidence,
        @NotBlank @Schema(description = "Chunk start time (ISO-8601)", example = "2025-01-01T12:00:00Z") String startTime,
        @NotBlank @Schema(description = "Chunk end time (ISO-8601)", example = "2025-01-01T12:00:02Z") String endTime
) {
}
